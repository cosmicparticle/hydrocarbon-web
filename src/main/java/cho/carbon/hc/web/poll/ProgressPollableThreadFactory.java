package cho.carbon.hc.web.poll;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.springframework.util.Assert;

public class ProgressPollableThreadFactory {
	
	ThreadPoolExecutor threadExecutor;

	Map<String, ProgressPollableThread> threadMap = new HashMap<>();
	
	public ProgressPollableThread createThread(ConsumerThrowException<WorkProgress> doWork, BiConsumer<WorkProgress, Exception> doWhenError, Consumer<WorkProgress> finallyDo) {
		return createThread(new WorkProgress(), doWork, doWhenError, finallyDo);
	}
	
	public ProgressPollableThread createThread(WorkProgress progress, ConsumerThrowException<WorkProgress> doWork, BiConsumer<WorkProgress, Exception> doWhenError, Consumer<WorkProgress> finallyDo) {
		Assert.notNull(progress, "进度对象不能为null");
		Assert.hasText(progress.getUUID(), "进度对象的UUID不能为空");
		Assert.state(!threadMap.containsKey(progress.getUUID()), "UUID为" + progress.getUUID() + "的线程已经被存在");
		synchronized (threadMap) {
			ProgressPollableThread thread = new ProgressPollableThread(progress) {
				
				@Override
				protected void doWork(WorkProgress progress) throws Exception {
					if(doWork != null) {
						doWork.accept(progress);
					}
				}
				
				@Override
				protected void doWhenError(WorkProgress progress, Exception e) {
					if(doWhenError != null) {
						doWhenError.accept(progress, e);
					}
				}
				
				@Override
				protected void finallyDo(WorkProgress progress) {
					if(finallyDo != null) {
						finallyDo.accept(progress);
					}
				}
			};
			thread.setExecutor(threadExecutor);
			threadMap.put(progress.getUUID(), thread);
			return thread;
		}
	}

	public WorkProgress getProgress(String uuid) {
		ProgressPollableThread thread = threadMap.get(uuid);
		if(thread != null) {
			return thread.getProgress();
		}
		return null;
	}

	public void removeProgress(String uuid) {
		WorkProgress progress = getProgress(uuid);
		if(progress != null) {
			Assert.isTrue(progress.isBreaked() || progress.isCompleted(), "被移除的线程必须是已经中断或者已经完成，当前不能完成该操作");
			threadMap.remove(uuid);
		}
	}

	public void stopWork(String uuid) {
		WorkProgress progress = getProgress(uuid);
		if(progress != null) {
			progress.setBreaked();
		}
	}
//
//	public ThreadPoolExecutor getThreadExecutor() {
//		return threadExecutor;
//	}
//
//	public void setThreadExecutor(ThreadPoolExecutor threadExecutor) {
//		this.threadExecutor = threadExecutor;
//	}
}
