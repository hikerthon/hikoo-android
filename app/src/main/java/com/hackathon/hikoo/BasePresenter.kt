package com.hackathon.hikoo

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

abstract class BasePresenter<T : IBase> : LifecycleObserver {

    private val subscriptions: CompositeDisposable by lazy { CompositeDisposable() }
    protected var view: T? = null

    protected fun Disposable.autoClear() {
        subscriptions += this
    }

    open fun attachView(view: T) {
        this.view = view
        view.provideLifecycleOwner().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun detachView() {
        subscriptions.dispose()
        view = null
    }
}

interface IBase {
    fun provideLifecycleOwner(): LifecycleOwner
}