-- RM   , NOME
-- 84198, Daiane Estenio
-- 86225, Denis Mantovani
-- 85142, Lucas Oliveira
-- 85398, Luís Paulino

-- Detalhes do projeto podem ser encontrados em: https://github.com/ozluuh/icorrespondencia-api
------------------------------------------------------------------------------------------------
-- Remova os comentários abaixo apenas se já possuir as tabelas registradas anteriormente.
-----------------------------
-- DROP SEQUENCE sq_person;

-- DROP TABLE person CASCADE CONSTRAINTS;

-- DROP TABLE users CASCADE CONSTRAINTS;

-- DROP TABLE townhouse CASCADE CONSTRAINTS;

-- Gera UUID
CREATE OR REPLACE FUNCTION NEWID RETURN CHAR IS
    guid VARCHAR(36);
BEGIN
    guid := SYS_GUID();
    guid := SUBSTR(guid,  1, 8) ||
        '-' || SUBSTR(guid,  9, 4) ||
        '-' || SUBSTR(guid, 13, 4) ||
        '-' || SUBSTR(guid, 17, 4) ||
        '-' || SUBSTR(guid, 21);
    RETURN guid;
END NEWID;
/

CREATE TABLE person (
    id NUMBER(19) PRIMARY KEY NOT NULL,
    name VARCHAR2(255) NOT NULL,
    public_id CHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT (systimestamp) NOT NULL,
    excluded_at TIMESTAMP,
    active NUMBER(1) DEFAULT 1 NOT NULL,
    person_type CHAR(1) DEFAULT 'F' NOT NULL
);

ALTER TABLE person ADD CHECK (active IN (0, 1));

-- F - Físico, J - Jurídico, N - Não definido/informado
ALTER TABLE person ADD CHECK (person_type IN ('F', 'J', 'N'));

CREATE TABLE users (
  id number(19) NOT NULL,
  email varchar2(255) UNIQUE,
  username varchar2(20) UNIQUE NOT NULL,
  password varchar2(128) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE townhouse (
  id number(19) NOT NULL,
  email varchar2(255) UNIQUE,
  cnpj varchar2(18) UNIQUE NOT NULL,
  site varchar2(255),
  PRIMARY KEY (id)
);

ALTER TABLE users ADD FOREIGN KEY (id) REFERENCES person (id);

ALTER TABLE townhouse ADD FOREIGN KEY (id) REFERENCES person (id);

CREATE SEQUENCE sq_person START WITH 1 NOCACHE ORDER;

-- Armazena os dados de pessoa: 'N'
CREATE OR REPLACE PROCEDURE store_person(
    p_name IN person.name%TYPE,
    p_person_type IN person.person_type%TYPE DEFAULT 'N',
    p_ret_id OUT person.id%TYPE
)
IS
  r_person person%ROWTYPE;
BEGIN
    INSERT INTO person (id, name, public_id, person_type)
    VALUES (sq_person.nextval, p_name, NEWID, p_person_type);

    IF p_person_type = 'N' THEN
        COMMIT;
    END IF;

    SELECT sq_person.CURRVAL
    INTO p_ret_id
    FROM dual;

    SELECT *
    INTO r_person
    FROM person
    WHERE id = p_ret_id;

    dbms_output.put_line('Person ' || r_person.public_id || ' record at ' || TO_CHAR(r_person.created_at, 'YYYY-MM-DD HH24:MI:SS') || ' created.');
EXCEPTION
    WHEN OTHERS THEN
        dbms_output.put_line(substr(SQLERRM, 1, 30));
END store_person;
/

-- Armazena os dados distintos de pessoas
-- Usuário: 'F'
CREATE OR REPLACE PROCEDURE store_user(
    p_name IN person.name%TYPE,
    p_username IN users.username%TYPE,
    p_password IN users.password%TYPE,
    p_email IN users.email%TYPE,
    p_ret_id OUT person.id%TYPE
)
IS
    r_user users%ROWTYPE;
    c_person_type person.person_type%TYPE := 'F';
BEGIN
    store_person(p_name, c_person_type, p_ret_id);

    INSERT INTO users(id, email, username, password)
    VALUES (p_ret_id, p_email, p_username, p_password);

    COMMIT;

    SELECT *
    INTO r_user
    FROM users
    WHERE id = p_ret_id;

    dbms_output.put_line('User ' || r_user.username || ' record with internal id ' || r_user.id || ' created.');
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        dbms_output.put_line('Cannot insert duplicate. Constraint ' || REGEXP_REPLACE(SQLERRM, '.*\((.*)\).*', '\1') || ' violated.');
        ROLLBACK;
        dbms_output.put_line('Canceled transaction.');
        p_ret_id := NULL;
    WHEN OTHERS THEN
        dbms_output.put_line(substr(SQLERRM, 1, 30));
END store_user;
/
-- Condomínio: 'J'
CREATE OR REPLACE PROCEDURE store_townhouse(
    p_company IN person.name%TYPE,
    p_cnpj IN townhouse.cnpj%TYPE,
    p_email IN townhouse.email%TYPE DEFAULT NULL,
    p_site IN townhouse.site%TYPE DEFAULT NULL,
    p_ret_id OUT person.id%TYPE
)
IS
    r_townhouse townhouse%ROWTYPE;
    c_person_type person.person_type%TYPE := 'J';
BEGIN
    store_person(p_company, c_person_type, p_ret_id);

    INSERT INTO townhouse(id, email, cnpj, site)
    VALUES (p_ret_id, p_email, p_cnpj, p_site);

    COMMIT;

    SELECT *
    INTO r_townhouse
    FROM townhouse
    WHERE id = p_ret_id;

    dbms_output.put_line('Townhouse ' || r_townhouse.cnpj || ' record with internal id ' || r_townhouse.id || ' created.');
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        dbms_output.put_line('Cannot insert duplicate. Constraint ' || REGEXP_REPLACE(SQLERRM, '.*\((.*)\).*', '\1') || ' violated.');
        ROLLBACK;
        dbms_output.put_line('Canceled transaction.');
        p_ret_id := NULL;
    WHEN OTHERS THEN
        dbms_output.put_line(substr(SQLERRM, 1, 30));
END store_townhouse;
/

-- Obtém o UUID de pessoa e:
-- Condomínio: número do CNPJ ou;
-- Usuários: nome de usuário ou;
-- Pessoa: o nome da pessoa.
CREATE OR REPLACE PROCEDURE retrieve_person(
    p_id IN person.id%TYPE,
    p_ret_public_id OUT person.public_id%TYPE,
    p_ret_username_cnpj OUT person.name%TYPE
)
IS
    v_person_type person.person_type%TYPE;
BEGIN
    SELECT
        p.public_id
        ,COALESCE(u.username, t.cnpj, p.name)
        ,p.person_type
    INTO
        p_ret_public_id
        ,p_ret_username_cnpj
        ,v_person_type
    FROM
        person p
    LEFT JOIN users u ON (p.id = u.id)
    LEFT JOIN townhouse t ON (p.id = t.id)
    WHERE
        p.id = p_id;

    IF v_person_type = 'F'
    THEN
        dbms_output.put_line('Obtained user data.');
    ELSIF v_person_type = 'J'
    THEN
        dbms_output.put_line('Obtained townhouse data.');
    ELSE
        dbms_output.put_line('Obtained person data.');
    END IF;

EXCEPTION
        WHEN NO_DATA_FOUND THEN
            dbms_output.put_line('Person ' || p_id || ' does not exist');
        WHEN OTHERS THEN
            dbms_output.put_line(substr(SQLERRM, 1, 30));
END retrieve_person;
/

-- Efetiva a transação
COMMIT;

-- Verifica a existência de dados antes de realizar as operações
-- SELECT * FROM person;
-- SELECT * FROM townhouse;
-- SELECT * FROM users;

-- Townhouse
DECLARE
    v_townhouse_id townhouse.id%TYPE;
    v_townhouse_company person.name%TYPE := 'Condomínio recanto das águas paradas';
    v_townhouse_email townhouse.email%TYPE;
    v_townhouse_cnpj townhouse.cnpj%TYPE;
    v_townhouse_site townhouse.site%TYPE;
BEGIN
    -- with required params
    v_townhouse_cnpj := '82.617.620/0001-39';
    dbms_output.put_line('PROCEDURE WITH REQUIRED PARAMS');
    store_townhouse(v_townhouse_company, v_townhouse_cnpj, p_ret_id => v_townhouse_id);
    IF v_townhouse_id IS NOT NULL THEN
        dbms_output.put_line('Procedure executed returns townhouse id ' || v_townhouse_id);
    ELSE
        dbms_output.put_line('Procedure executed without return');
    END IF;

    dbms_output.put_line('-----------------------------------------------------------------');

    -- with all params
    v_townhouse_company := 'Condomínio rosas azuis';
    v_townhouse_email := 'rosas@fakemail.com';
    v_townhouse_cnpj := '48.096.148/0001-06';
    v_townhouse_site := 'www.azuisrosas.com.br';
    dbms_output.put_line('PROCEDURE WITH ALL PARAMS');
    store_townhouse(v_townhouse_company, v_townhouse_cnpj, v_townhouse_email, v_townhouse_site, v_townhouse_id);
    IF v_townhouse_id IS NOT NULL THEN
        dbms_output.put_line('Procedure executed returns townhouse id ' || v_townhouse_id);
    ELSE
        dbms_output.put_line('Procedure executed without return');
    END IF;
END;
/

-- Verifica a existência de dados após a operação de inserção sendo que apenas a
-- tabela de usuários não deve ter sido populada ainda
-- SELECT * FROM person;
-- SELECT * FROM townhouse;
-- SELECT * FROM users;

-- Users
DECLARE
    v_user_id users.id%TYPE;
    v_user_name person.name%TYPE := 'Benício Vicente Rezende';
    v_user_email users.email%TYPE := 'rezendebenicio_@lphbrasil.com.br';
    v_user_username users.username%TYPE := 'rezende.benicio';
    v_user_password users.password%TYPE := 'ySQUC0h5SJ';
BEGIN
    -- with required params
    dbms_output.put_line('PROCEDURE WITH REQUIRED PARAMS');
    store_user(v_user_name, v_user_username, v_user_password, v_user_email, v_user_id);
    IF v_user_id IS NOT NULL THEN
        dbms_output.put_line('Procedure executed returns user id ' || v_user_id);
    ELSE
        dbms_output.put_line('Procedure executed without return');
    END IF;

    dbms_output.put_line('-----------------------------------------------------------------');

    -- with all params
    v_user_name := 'Simone Sabrina Jéssica Fernandes';
    v_user_email := 'simonesabrinajessicafernandes_@kimmay.com.br';
    v_user_username := 'sabrinasimone';
    v_user_password := 'GgTfu1HokW';
    store_user(v_user_name, v_user_username, v_user_password, v_user_email, v_user_id);
    IF v_user_id IS NOT NULL THEN
        dbms_output.put_line('Procedure executed returns user id ' || v_user_id);
    ELSE
        dbms_output.put_line('Procedure executed without return');
    END IF;
END;
/

-- Verifica a existência de dados após a operação de inserção sendo que apenas a
-- tabela de condomínios não deve ter sofrido alterações
-- SELECT * FROM person;
-- SELECT * FROM users;
-- SELECT * FROM townhouse;

-- Obtém o public_id de Pessoa e caso:
--  - Condomínio.: o número do CNPJ;
--  - Usuário....: o nome de usuário de acesso;
--  - Pessoa.....: o nome da pessoa;
DECLARE
    v_person_public_id person.public_id%TYPE;
    v_person_username_cnpj person.name%TYPE;
    l_person_id person.id%TYPE := &findPersonByIdEq;
BEGIN
    retrieve_person(l_person_id, v_person_public_id, v_person_username_cnpj);

    IF v_person_public_id IS NOT NULL THEN
        dbms_output.put_line('Data get with id ' || l_person_id || ' public_id eq ' || v_person_public_id || ' and identifier eq ' || v_person_username_cnpj);
    END IF;
END;
/
