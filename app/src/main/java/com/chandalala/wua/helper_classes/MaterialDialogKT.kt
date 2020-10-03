package com.chandalala.wua.helper_classes

import android.app.Activity
import android.text.InputType
import android.util.Log
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.chandalala.wua.R
import com.chandalala.wua.database.WUA_RetrofitAPI_DataProvider
import com.developer.kalert.KAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MaterialDialogKT(val activity: Activity) {


    fun showDialog(){

        val type = InputType.TYPE_NUMBER_VARIATION_PASSWORD
        var oldPassword: String
        var newPassword: String

        MaterialDialog(activity).show {

           input(allowEmpty = false ,maxLength = 4 ,hint = "Old password", inputType = type) { _, charSequence ->
                    oldPassword=charSequence.toString()
                    Toast.makeText(activity, " $oldPassword", Toast.LENGTH_SHORT).show()
                    dismiss()

               MaterialDialog(activity).show {
                   input(allowEmpty = false ,maxLength = 4 ,hint = "New password", inputType = type) { _, charSequence ->
                       newPassword = charSequence.toString()

                           Toast.makeText(activity, "$oldPassword $newPassword", Toast.LENGTH_SHORT).show()

                           val wuaRetrofitAPIdataProvider: WUA_RetrofitAPI_DataProvider
                           val sharedPref = UserSharedPrefefences(activity)

                           //Execute get requests
                           val retrofit = Retrofit.Builder()
                                   .baseUrl("http://10.0.2.2/phptutorials/AndroidPhp/")
                                   .addConverterFactory(GsonConverterFactory.create())
                                   .build()

                           wuaRetrofitAPIdataProvider = retrofit.create(WUA_RetrofitAPI_DataProvider::class.java)

                           //Executing the network request
                           val call = wuaRetrofitAPIdataProvider.changePassword(sharedPref.readLoginStatus().student_number,
                                   oldPassword, newPassword)

                           var kAlertDialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)

                           call.enqueue(object : Callback<String> {
                               override fun onResponse(call: Call<String>, response: Response<String>) {

                                   dismiss()

                                    if(response.isSuccessful && response.body()=="ok"){

                                        kAlertDialog = KAlertDialog(activity, KAlertDialog.SUCCESS_TYPE)
                                        kAlertDialog
                                                .setContentText("Password change successful")
                                                .setConfirmClickListener {

                                                    kAlertDialog.dismissWithAnimation()
                                                }
                                                .confirmButtonColor(R.drawable.button_selector)
                                                .setConfirmText("ok")
                                                .show()
                                    }
                                   else{

                                        kAlertDialog
                                                .setContentText("Password change Failed")
                                                .setConfirmClickListener {

                                                    kAlertDialog.dismissWithAnimation()
                                                }
                                                .confirmButtonColor(R.drawable.button_selector)
                                                .setConfirmText("ok")
                                                .show()

                                    }

                               }

                               override fun onFailure(call: Call<String>, t: Throwable) {

                                   dismiss()

                                   Log.d("MaterialDialogKT", ""+t.message)

                                   kAlertDialog
                                           .setContentText("Server error try again letter")
                                           .setConfirmClickListener {

                                               kAlertDialog.dismissWithAnimation()
                                           }
                                           .confirmButtonColor(R.drawable.button_selector)
                                           .setConfirmText("ok")
                                           .show()

                               }
                           })

                   }
               }
           }
           positiveButton(R.string.dialog_ok)
           negativeButton(R.string.dialog_cancel){
                    dismiss()
           }

       }

    }


}