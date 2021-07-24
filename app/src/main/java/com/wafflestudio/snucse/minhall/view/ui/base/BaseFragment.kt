package com.wafflestudio.snucse.minhall.view.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment : Fragment() {

    private val onPauseCompositeDisposable = CompositeDisposable()
    private val onDestroyCompositeDisposable = CompositeDisposable()
    private val onDestroyViewCompositeDisposable = CompositeDisposable()
    private val onDetachCompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preventTouchUnderneathFragment(view)
    }

    private fun preventTouchUnderneathFragment(root: View) {
        root.isFocusable = true
        root.isClickable = true
    }

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

    protected fun showNoticeDialog(title: String, body: String, action: String) {
        NoticeDialogFragment.show(childFragmentManager, title, body, action)
    }

    protected fun showAlertDialog(body: String, onConfirm: () -> Unit, onCancel: (() -> Unit)?) {
        AlertDialogFragment.show(childFragmentManager, body, onConfirm, onCancel)
    }
}