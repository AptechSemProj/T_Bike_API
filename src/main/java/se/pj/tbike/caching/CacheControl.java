package se.pj.tbike.caching;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

import java.time.Duration;

public final class CacheControl {

	private final TaskScheduler scheduler;
	private final Trigger trigger;

	private ScheduledFuture<?> runningTask;

	private CacheControl(TaskScheduler scheduler, Trigger trigger) {
		if ( scheduler == null ) {
			throw new IllegalArgumentException();
		}
		if ( trigger == null ) {
			throw new IllegalArgumentException();
		}
		this.scheduler = scheduler;
		this.trigger = trigger;
	}

	public CacheControl(TaskScheduler scheduler, Duration maxLifeTime) {
		this( scheduler, createTrigger( maxLifeTime ) );
	}

	public CacheControl() {
		this.scheduler = null;
		this.trigger = null;
	}

	public static CacheControl copy(CacheControl other) {
		return new CacheControl( other.scheduler, other.trigger );
	}

	private static Trigger createTrigger(Duration maxLifeTime) {
		if ( maxLifeTime == null || maxLifeTime.isNegative() ) {
			throw new IllegalArgumentException();
		}
		PeriodicTrigger trigger = new PeriodicTrigger( maxLifeTime );
		trigger.setFixedRate( true );
		trigger.setInitialDelay( maxLifeTime );
		return trigger;
	}

	private boolean cleanable() {
		return scheduler != null && trigger != null;
	}

	// package-private
	void start(Runnable task) {
		if ( task == null ) {
			throw new IllegalArgumentException();
		}
		if ( !cleanable() || isStarted() ) {
			return;
		}
		runningTask = scheduler.schedule( task, trigger );
	}

	// package-private
	void end() {
		if ( isRunning() ) {
			runningTask.cancel( true );
			runningTask = null;
		}
	}

	// package-private
	void restart(Runnable task) {
		end();
		start( task );
	}

	// package-private
	boolean isStarted() {
		return runningTask != null;
	}

	// package-private
	boolean isRunning() {
		return isStarted() && !runningTask.isDone() &&
				!runningTask.isCancelled();
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof CacheControl that) ) {
			return false;
		}
		return Objects.equals( trigger, that.trigger ) &&
				Objects.equals( runningTask, that.runningTask );
	}

	@Override
	public int hashCode() {
		return Objects.hash( trigger, runningTask );
	}
}
