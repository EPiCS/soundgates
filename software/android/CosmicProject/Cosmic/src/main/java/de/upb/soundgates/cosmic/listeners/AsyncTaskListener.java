package de.upb.soundgates.cosmic.listeners;

/**
 * Created by posewsky on 10.11.13.
 */
public interface AsyncTaskListener<E> {
    void onAsyncTaskCompletion(E result);
    void onAsyncTaskFailure(E error);
}
