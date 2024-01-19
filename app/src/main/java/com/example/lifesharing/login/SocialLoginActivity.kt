package com.example.lifesharing.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.lifesharing.MainActivity
import com.example.lifesharing.R
import com.example.lifesharing.databinding.ActivitySocialLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

class SocialLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySocialLoginBinding
    val socialLoginViewModel : SocialLoginViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_social_login)
        binding.viewModel = socialLoginViewModel
        binding.activity = this
        binding.lifecycleOwner = this
        setObserve()
    }

    fun setObserve() {
        socialLoginViewModel.navigatedLoginActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        socialLoginViewModel.navigatedRegisterActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
        socialLoginViewModel.navigatedResetPasswordActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
        socialLoginViewModel.navigatedMainActivity.observe(this) {
            if(it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    // 구글 로그인이 성공한 결과값을 받는 함수

    var googleLoginResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->

        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        account.idToken // 로그인한 사용자 정보를 암호화한 값
        socialLoginViewModel.firebaseAuthWithGoogle(account.idToken)
    }

}