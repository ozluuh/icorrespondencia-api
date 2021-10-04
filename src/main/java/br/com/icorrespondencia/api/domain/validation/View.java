package br.com.icorrespondencia.api.domain.validation;

public interface View {

    interface Public {
    }

    interface Private extends Public {
    }

    interface Specific {
    }

    interface Hidden {
    }
}
