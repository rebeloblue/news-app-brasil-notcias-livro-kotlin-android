package thiengo.com.br.newsapp_brasilnotcias.fragment

import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_sign_up_access.*
import kotlinx.android.synthetic.main.fragment_sign_up_terms.*
import me.drakeet.materialdialog.MaterialDialog
import thiengo.com.br.newsapp_brasilnotcias.R
import thiengo.com.br.newsapp_brasilnotcias.logic.SignUpViewModel


class SignUpTermsFragment :
        Fragment(),
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener {

    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(
            R.layout.fragment_sign_up_terms,
            container,
            false)
    }

    override fun onCheckedChanged(
        view: CompoundButton?,
        status: Boolean) {

        bt_send.isEnabled = status
    }

    override fun onClick(view: View?) {

        val layout = LayoutInflater
            .from(activity)
            .inflate(R.layout.dialog_personal, null, false)

        val tvName = layout.findViewById<TextView>(R.id.tv_name)
        tvName.setText( signUpViewModel.user.name )

        val tvProfession = layout.findViewById<TextView>(R.id.tv_profession)
        tvProfession.setText( signUpViewModel.user.profession )

        val ivProfile = layout.findViewById<RoundedImageView>(R.id.iv_profile)
        ivProfile.setImageURI( Uri.parse( signUpViewModel.user.imagePath ) )

        MaterialDialog(activity)
            .setContentView( layout )
            .setCanceledOnTouchOutside(true)
            .show()
    }

    override fun onStart() {
        super.onStart()

        signUpViewModel = ViewModelProviders.of(activity!!).get(SignUpViewModel::class.java)

        cb_terms.setOnCheckedChangeListener(this)
        bt_send.setOnClickListener(this)

        onCheckedChanged( null, signUpViewModel.user.statusTerms ?: false )
    }

    override fun onPause() {
        super.onPause()

        signUpViewModel.updateUserTermsData( cb_terms.isChecked )
    }
}