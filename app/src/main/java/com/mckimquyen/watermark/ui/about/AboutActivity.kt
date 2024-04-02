package com.mckimquyen.watermark.ui.about

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.children
import androidx.core.widget.TextViewCompat
import androidx.palette.graphics.Palette
import com.jakewharton.processphoenix.ProcessPhoenix
import com.mckimquyen.cmonet.CMonet
import com.mckimquyen.watermark.BuildConfig
import com.mckimquyen.watermark.R
import com.mckimquyen.watermark.databinding.AAboutBinding
import com.mckimquyen.watermark.utils.ktx.bgColor
import com.mckimquyen.watermark.utils.ktx.colorBackground
import com.mckimquyen.watermark.utils.ktx.colorPrimary
import com.mckimquyen.watermark.utils.ktx.colorSecondaryContainer
import com.mckimquyen.watermark.utils.ktx.inflate
import com.mckimquyen.watermark.utils.ktx.openLink
import com.mckimquyen.watermark.utils.ktx.titleTextColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {

    private val binding by inflate<AAboutBinding>()

    private val viewModel: AboutViewModel by viewModels()

    private lateinit var bgDrawable: GradientDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        changeStatusBarStyle()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        window?.navigationBarColor = Color.TRANSPARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window?.navigationBarDividerColor = Color.TRANSPARENT
        }
    }

    private fun changeStatusBarStyle() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.findViewById<View>(android.R.id.content)?.foreground = null
    }


    private fun initView() {
        with(binding) {
            bgDrawable = ContextCompat.getDrawable(
                this@AboutActivity,
                R.drawable.bg_gradient_about_page
            ) as GradientDrawable

            this.root.background = bgDrawable
//            tvVersion.setOnClickListener {
//                openLink("https://github.com/rosuH/EasyWatermark/releases/")
//            }
            tvVersionValue.text = BuildConfig.VERSION_NAME
            tvRating.setOnClickListener {
//                openLink(Uri.parse("https://play.google.com/store/apps/details?id=com.mckimquyen.kqxs"))
                openLink(Uri.parse("https://play.google.com/store/apps/details?id=${it.context.packageName}"))
            }
            tvMoreApp.setOnClickListener {
                openLink("https://play.google.com/store/apps/developer?id=McKimQuyen")
            }
//            tvChangeLog.setOnClickListener {
//                openLink("https://github.com/rosuH/EasyWatermark/releases/")
//            }
//            tvOpenSource.setOnClickListener {
//                kotlin.runCatching {
//                    startActivity(
//                        Intent(
//                            this@AboutActivity,
//                            OpenSourceActivity::class.java
//                        )
//                    )
//                }
//            }
//            tvPrivacyCn.setOnClickListener {
//                openLink(Uri.parse("https://github.com/rosuH/EasyWatermark/blob/master/PrivacyPolicy_zh-CN.md"))
//            }
            tvPrivacyEng.setOnClickListener {
                openLink(Uri.parse("https://loitp.notion.site/loitp/Privacy-Policy-319b1cd8783942fa8923d2a3c9bce60f"))
            }
//            civAvatar.setOnClickListener {
//                openLink("https://github.com/rosuH")
//            }
//            civAvatarDesigner.setOnClickListener {
//                openLink("https://tovi.fun/")
//            }
            ivBack.setOnClickListener {
                finish()
            }

            switchDebug.setOnCheckedChangeListener { _, isChecked ->
                viewModel.toggleBounds(isChecked)
            }

            switchDynamicColor.isChecked = CMonet.isDynamicColorAvailable()

            switchDynamicColor.setOnCheckedChangeListener { _, isChecked ->
                viewModel.toggleSupportDynamicColor(isChecked)
                Toast.makeText(
                    /* context = */ this@AboutActivity,
                    /* text = */ getString(R.string.you_ll_need_to_close_and_restart_the_app_to_switch_themes),
                    /* duration = */ Toast.LENGTH_SHORT
                ).show()
                ProcessPhoenix.triggerRebirth(this@AboutActivity)
            }

            binding.clDevContainer.backgroundTintList =
                ColorStateList.valueOf(this@AboutActivity.colorSecondaryContainer)
            binding.clDesignerContainer.backgroundTintList =
                ColorStateList.valueOf(this@AboutActivity.colorSecondaryContainer)

            viewModel.waterMark.observe(this@AboutActivity) {
                switchDebug.isChecked = viewModel.waterMark.value?.enableBounds ?: false
            }

            viewModel.palette.observe(this@AboutActivity) {
                when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                        applyPaletteForSupportNight(it)
                    }

                    it == null -> {
                        binding.clContainer.children
                            .plus(binding.tvTitle)
                            .plus(binding.tvSubTitle)
                            .plus(binding.tvTitleDesigner)
                            .plus(binding.tvSubTitleDesigner)
                            .forEach { view ->
                                if (view !is TextView) {
                                    return@forEach
                                }
                                view.setTextColor(Color.WHITE)
                                TextViewCompat.setCompoundDrawableTintList(
                                    view,
                                    ColorStateList.valueOf(Color.WHITE)
                                )
                            }
                        return@observe
                    }

                    else -> {
                        applyPaletteForSupportLightStatusIcon(it)
                    }
                }
            }
        }
    }

    private fun applyPaletteForSupportNight(palette: Palette?) {
        val bgColor = palette?.bgColor(this@AboutActivity) ?: this@AboutActivity.colorPrimary
        val bgAccent = palette?.bgColor(this@AboutActivity) ?: this@AboutActivity.colorBackground
        val colorList = arrayOf(
            ColorUtils.setAlphaComponent(bgColor, 255),
            ColorUtils.setAlphaComponent(bgAccent, 65),
        ).toIntArray()
        bgDrawable.colors = colorList
    }

    private fun applyPaletteForSupportLightStatusIcon(palette: Palette) {
        val bgColor = palette.bgColor(this@AboutActivity)
        val bgAccent = palette.bgColor(this@AboutActivity)
        val colorList = arrayOf(
            ColorUtils.setAlphaComponent(bgColor, 255),
            ColorUtils.setAlphaComponent(bgAccent, 65),
        ).toIntArray()
        bgDrawable.colors = colorList

        val textColor = palette.titleTextColor(this@AboutActivity)
        binding.clContainer.children
            .plus(binding.tvTitle)
            .plus(binding.tvSubTitle)
            .plus(binding.tvTitleDesigner)
            .plus(binding.tvSubTitleDesigner)
            .forEach { view ->
                if (view !is TextView) {
                    return@forEach
                }
                view.setTextColor(textColor)
                TextViewCompat.setCompoundDrawableTintList(
                    view,
                    ColorStateList.valueOf(textColor)
                )
            }
    }

//    private fun applyPaletteForNoMatterWhoYouAre(palette: Palette) {
//        val bgColor = palette.bgColor(this@AboutActivity)
//        val bgAccent = palette.bgColor(this@AboutActivity)
//        val colorList = arrayOf(
//            ColorUtils.setAlphaComponent(bgColor, 255),
//            ColorUtils.setAlphaComponent(bgAccent, 65),
//        ).toIntArray()
//        bgDrawable.colors = colorList
//
//        val textColor = palette.titleTextColor(this@AboutActivity)
//        binding.clContainer.children
//            .plus(binding.tvTitle)
//            .plus(binding.tvSubTitle)
//            .plus(binding.tvTitleDesigner)
//            .plus(binding.tvSubTitleDesigner)
//            .forEach { view ->
//                if (view !is TextView) {
//                    return@forEach
//                }
//                view.setTextColor(textColor)
//                TextViewCompat.setCompoundDrawableTintList(
//                    view,
//                    ColorStateList.valueOf(textColor)
//                )
//            }
//    }
}
