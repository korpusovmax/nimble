package me.korpusovmax.nimble;

public class Either<TSuccess, TError> {
    private TError error;
    private TSuccess success;

    private Either (TSuccess success, TError error) {
        this.success = success;
        this.error = error;
    }

    public static <TSuccess> Either success(TSuccess value) {
        return new Either(value, null);
    }
    public static <TError> Either error (TError value) {
        return new Either(null, value);
    }

    public Boolean error() {
        return this.error != null;
    }
    //interim solution (the RuntimeResult instead of this 💩 could be better)
    public void register(Either child) {
        error = (TError) child.getError();
        success = (TSuccess) child.getSuccess();
    }
    public TError getError() {
        return error;
    }
    public TSuccess getSuccess() {
        return success;
    }
}
