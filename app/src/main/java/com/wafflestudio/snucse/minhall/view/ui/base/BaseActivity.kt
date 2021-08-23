package com.wafflestudio.snucse.minhall.view.ui.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    private val onPauseCompositeDisposable = CompositeDisposable()
    private val onDestroyCompositeDisposable = CompositeDisposable()

    override fun onPause() {
        super.onPause()
        onPauseCompositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyCompositeDisposable.clear()
    }

    protected fun Disposable.disposeOnPause(): Disposable = this.apply {
        onPauseCompositeDisposable.add(this)
    }

    protected fun Disposable.disposeOnDestroy(): Disposable = this.apply {
        onDestroyCompositeDisposable.add(this)
    }

    open fun toSetting() = Unit
}
