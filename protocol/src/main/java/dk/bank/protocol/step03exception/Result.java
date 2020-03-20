package dk.bank.protocol.step03exception;

import java.util.function.Consumer;
import java.util.function.Function;

public class Result<PAYLOAD> {
    private final PAYLOAD value;
    private final Exception error;

    private Result(PAYLOAD value, Exception error) {
        this.value = value;
        this.error = error;
    }

    /**
     * This is the unit method
     */
    public static <RESULTING> Result<RESULTING> ok(RESULTING value) {
        if (value == null) return new Result<>(null, new Exception("No value"));
        return new Result<>(value, null);
    }

    public static <RESULTING> Result<RESULTING> error(Exception error) {
        return new Result<>(null, error);
    }

    /**
     * This is the bind method
     */
    public <RESULTING> Result<RESULTING> flatMap(Function<PAYLOAD, Result<RESULTING>> mapper) {

        if (this.isError()) {
            return Result.error(error);
        }

        return mapper.apply(value);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }

    public PAYLOAD getValue() {
        return value;
    }

    public Exception getError() {
        return error;
    }

    /**
     * If a value is success, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is success
     * @throws NullPointerException if value is present and {@code consumer} is
     *                              null
     */
    public Result<PAYLOAD> ifSuccess(Consumer<? super PAYLOAD> consumer) {
        if (value != null)
            consumer.accept(value);
        return this;
    }

    /**
     * If a value is failed, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is null
     */
    public Result<PAYLOAD> ifFailure(Consumer<? super Exception> consumer) {
        if (error != null)
            consumer.accept(error);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        if(value!=null)sb.append("value=").append(value);
        if(value==null)sb.append("error=").append(error);
        sb.append('}');
        return sb.toString();
    }
}