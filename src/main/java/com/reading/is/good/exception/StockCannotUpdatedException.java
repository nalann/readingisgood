package com.reading.is.good.exception;

public class StockCannotUpdatedException extends RuntimeException  {
	public StockCannotUpdatedException(String errorMessage) {
        super(errorMessage);
    }
}
