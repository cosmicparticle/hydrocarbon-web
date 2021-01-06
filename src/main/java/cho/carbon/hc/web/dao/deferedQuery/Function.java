package cho.carbon.hc.web.dao.deferedQuery;

public interface Function<T, R> {
	R apply(T var1);
}