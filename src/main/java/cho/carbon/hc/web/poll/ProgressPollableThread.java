package cho.carbon.hc.web.poll;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.util.Assert;

public abstract class ProgressPollableThread {
	private final WorkProgress progress;
	private ThreadPoolExecutor executor;
	
	
	public ProgressPollableThread() {
		this(new WorkProgress());
	}
	public ProgressPollableThread(WorkProgress progress) {
		super();
		Assert.notNull(progress, "进度对象不能为空");
		this.progress = progress;
	}
	
	protected abstract void doWork(WorkProgress progress) throws Exception;
	protected void doWhenError(WorkProgress progress, Exception e) {
		
	}
	protected void finallyDo(WorkProgress progress) {
		
	}
	
	public final void start() {
		Runnable runnable = getWork();
		if(runnable != null) {
			startThread(runnable);
		}
	}


	private void startThread(Runnable runnable) {
		if(executor != null) {
			executor.execute(runnable);
		}else {
			new Thread(runnable).start();
		}
	}


	private Runnable getWork() {
		return new Runnable() {
			
			@Override
			public void run() {
				try {
					doWork(progress);
				}catch (Exception e) {
					doWhenError(progress, e);
					//设置当前的报错信息
					progress.setGlobalException(e);
					if(!progress.isCompleted()) {
						progress.setBreaked();
					}
					progress.getLogger().error("执行时发生错误，已中断线程");
				}finally {
					finallyDo(progress);
				}
			}
		};
	}


	public ThreadPoolExecutor getExecutor() {
		return executor;
	}


	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}
	public WorkProgress getProgress() {
		return progress;
	}
	
	
}
