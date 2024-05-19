package com.sacidpak.courier.tracking.exception;

import java.io.Serial;

public class NotFoundException extends BaseException {

    @Serial
    private static final long serialVersionUID = -7901206092909040975L;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException() {
    }
}
