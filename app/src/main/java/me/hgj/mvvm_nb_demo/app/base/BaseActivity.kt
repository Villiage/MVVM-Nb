package me.hgj.mvvm_nb_demo.app.base

import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import me.hgj.mvvm_nb.BaseViewModel
import me.hgj.mvvm_nb.BaseVmDbActivity
import me.hgj.mvvm_nb_demo.R
import me.hgj.mvvm_nb_demo.app.ext.getAppViewModel
import me.hgj.mvvm_nb_demo.viewmodel.AppViewModel

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/21
 * 描述　: 你项目中的Activity基类，在这里实现显示弹窗，吐司，还有加入自己的需求操作 ，如果不想用Databind，请继承
 * BaseVmActivity例如
 * abstract class BaseActivity<VM : BaseViewModel> : BaseVmActivity<VM>() {
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {

    private var dialog: MaterialDialog? = null

    val appViewModel: AppViewModel by lazy { getAppViewModel() }

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
        if (dialog == null) {
            dialog = this.let {
                MaterialDialog(it)
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .customView(R.layout.custom_progress_dialog_view)
                    .lifecycleOwner(this)
            }
            dialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.loading_tips).text = message
            }
        }
        dialog?.show()
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
        dialog?.dismiss()
    }

    /**
     * 显示消息弹窗
     */
    override fun showMessage(message: String) {
        MaterialDialog(this)
            .cancelable(false)
            .lifecycleOwner(this)
            .show {
                title(text = "温馨提示")
                message(text = message)
                positiveButton(text = "确定")
            }
    }

    /**
     * 吐司
     */
    override fun showToast(message: String) {
        ToastUtils.showShort(message)
    }

}