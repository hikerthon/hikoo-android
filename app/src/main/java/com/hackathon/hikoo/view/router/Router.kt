package com.hackathon.hikoo.view.router

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class Router(
    val fragmentManager: FragmentManager,
    @IdRes protected val containerId: Int,
    var transactionReadyCallback: OnTransactionReadyCallback?
) {

    fun replaceFragment(fragment: Fragment, tag: String, addToBackStack: Boolean = true) {
        replaceFragment(fragment, tag, addToBackStack, -1, -1, -1, -1)
    }

    fun replaceFragment(
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = true,
        @AnimRes enterAnimId: Int,
        @AnimRes exitAnimId: Int,
        @AnimRes popEnterAnimId: Int,
        @AnimRes popExitAnimId: Int
    ) {
        val transaction = fragmentManager.beginTransaction()
        if (enterAnimId != -1 && exitAnimId != -1 && popEnterAnimId != -1 && popExitAnimId != -1) {
            transaction.setCustomAnimations(enterAnimId, exitAnimId, popEnterAnimId, popExitAnimId)
        }

        transaction.replace(containerId, fragment, tag)

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }

        val readyForTransaction = transactionReadyCallback?.isReadyForTransaction() ?: false
        if (readyForTransaction) {
            transaction.commit()
        } else {
            transaction.commitAllowingStateLoss()
        }
    }

    fun clean() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun release() {
        transactionReadyCallback = null
    }

    interface OnTransactionReadyCallback {
        fun isReadyForTransaction(): Boolean
    }
}