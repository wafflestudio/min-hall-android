package com.wafflestudio.snucse.minhall.view.ui

import androidx.fragment.app.DialogFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseDialogFragment : DialogFragment() {

    private val onPauseCompositeDisposable = CompositeDisposable()
    private val onDestroyCompositeDisposable = CompositeDisposable()
    private val onDestroyViewCompositeDisposable = CompositeDisposable()
    private val onDetachCompositeDisposable = CompositeDisposable()

    override fun onPause() {
        super.onPause()
        onPauseCompositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyCompositeDisposable.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyViewCompositeDisposable.clear()
    }

    override fun onDetach() {
        super.onDetach()
        onDetachCompositeDisposable.clear()
    }


    protected fun Disposable.disposeOnPause(): Disposable = this.apply {
        onPauseCompositeDisposable.add(this)
    }

    protected fun Disposable.disposeOnDestroy(): Disposable = this.apply {
        onDestroyCompositeDisposable.add(this)
    }

    protected fun Disposable.disposeOnDestroyView(): Disposable = this.apply {
        onDestroyViewCompositeDisposable.add(this)
    }

    protected fun Disposable.disposeOnDetach(): Disposable = this.apply {
        onDetachCompositeDisposable.add(this)
    }
}
