package cho.carbon.hc.web.poll;

@FunctionalInterface
public interface ConsumerThrowException<T> {
	void accept(T arg) throws Exception;
}
