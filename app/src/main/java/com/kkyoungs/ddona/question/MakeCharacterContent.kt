package com.kkyoungs.ddona.question

import android.R.attr.button
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.FragmentMakeCharacterBinding
import com.kkyoungs.ddona.question.data.MbtiCalculationResponse
import com.kkyoungs.ddona.question.data.QuestionData
import com.kkyoungs.ddona.question.data.QuestionSelectList
import com.kkyoungs.ddona.retrofit.MbtiCalculationRequest
import com.kkyoungs.ddona.retrofit.RetrofitClient
import com.kkyoungs.ddona.retrofit.ShopService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MakeCharacterContent : Fragment() {
    private var binding :FragmentMakeCharacterBinding ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_make_character, container, false)
        init()
        stepBar()

        getService()
        click()
        return binding!!.root
    }
    private fun init(){
        binding!!.llBack.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frame, GoInfoContent())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
    }
    private var questionNumber = 1
    var editName : EditText ?=null
    var answer1 :String ?= null
    var answer2 :String ?= null
    var answer3 :String ?= null
    private fun stepBar() {
        val qlist = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)


        val gridBlockAdapter = GridBlockAdapter(requireContext(), qlist)
        binding!!.gridView.apply {
            adapter = gridBlockAdapter
            layoutManager = GridLayoutManager(context, 10)

        }
        gridBlockAdapter.position = questionNumber - 1
        binding!!.tvTitleQ.text = "Q" + questionNumber
        editName = binding!!.etName
        if(questionNumber == 19){
            binding!!.qWrite.visibility = View.VISIBLE
            binding!!.qCheck.visibility = View.GONE
            binding!!.llCompleteCharacter.visibility = View.GONE
            binding!!.tvQuestion.text = getString(R.string.last_q19)
            editName!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                @SuppressLint("ResourceAsColor")
                override fun afterTextChanged(editable: Editable) {
                    if (editable.isNotEmpty()) {
                        binding!!.btnComplete.isClickable = true
                        binding!!.btnComplete.setBackgroundResource(R.drawable.ic_question_btn_click)
                        binding!!.btnComplete.setTextColor(Color.WHITE)
                    } else {
                        binding!!.btnComplete.isClickable = false
                        binding!!.btnComplete.setBackgroundResource(R.drawable.ic_question_btn)
                        binding!!.btnComplete.setTextColor(Color.GRAY)
                    }

                }
            })
            editName!!.setOnEditorActionListener{ textView, action, event ->
                var handled = false

                if (action == EditorInfo.IME_ACTION_DONE) {
                    // 키보드 내리기
                    val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding!!.etName.windowToken, 0)
                    handled = true
                    binding!!.btnComplete.isPressed
                }

                handled

            }
        }else {
            binding!!.qWrite.visibility = View.GONE
            binding!!.qCheck.visibility = View.VISIBLE
            binding!!.llCompleteCharacter.visibility = View.GONE

            val callGet = RetrofitClient.apiService.getMbtiQuestion(questionNumber)
            callGet.enqueue(object : Callback<QuestionData> {
                override fun onResponse(
                    call: Call<QuestionData>,
                    response: Response<QuestionData>
                ) {
                    if (response.isSuccessful) {
                        val mbtiQuestionResponse = response.body()

                        binding!!.tvQuestion.text = mbtiQuestionResponse?.quenstion.toString()
                        val answer = mbtiQuestionResponse?.answers
                        answer1 = answer?.get(0)
                        answer2 = answer?.get(1)
                        answer3 = answer?.get(2)

                        val regex = "[#IENSFTJP?]"
                        val firstQt = answer?.get(0)?.replace(regex.toRegex(), "")
                        val secondQt = answer?.get(1)?.replace(regex.toRegex(), "")
                        val thirdQt = answer?.get(2)?.replace(regex.toRegex(), "")
                        binding!!.btnQ1.text = firstQt
                        binding!!.btnQ2.text = secondQt
                        binding!!.btnQ3.text = thirdQt

                    } else {
                        Toast.makeText(context,"서버 에러",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<QuestionData>, t: Throwable) {
                    Toast.makeText(context,"네트워크 에러", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private var questionSelectData: QuestionSelectList? = null

    var selectList = ArrayList<QuestionSelectList>()
    private fun click (){

        binding!!.btnQ1.setOnClickListener {
            questionNumber++
            questionSelectData = QuestionSelectList(answer1.toString())
            selectList.add(questionSelectData!!)

            stepBar()
            caculatorMbti()
        }
        binding!!.btnQ2.setOnClickListener {
            questionNumber++
            questionSelectData = QuestionSelectList(answer2.toString())
            selectList.add(questionSelectData!!)

            stepBar()
            caculatorMbti()
        }

        binding!!.btnQ3.setOnClickListener {
            questionNumber++
            questionSelectData = QuestionSelectList(answer3.toString())
            selectList.add(questionSelectData!!)

            stepBar()
            caculatorMbti()
        }

        binding!!.btnComplete.setOnClickListener {
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding!!.etName.windowToken, 0)
            calculator()
        }
    }
    var totalE = 0
    var totalI = 0
    var totalN = 0
    var totalS = 0
    var totalT = 0
    var totalF = 0
    var totalJ = 0
    var totalP = 0
    private fun caculatorMbti(){

        for (i in 0 until selectList.size) {
            if (selectList[i].quenstion.contains("E")) {
                ++totalE
            } else if (selectList[i].quenstion.contains("I")) {
                ++totalI
            } else if (selectList[i].quenstion.contains("N")) {
                ++totalN
            } else if (selectList[i].quenstion.contains("S")) {
                ++totalS
            } else if (selectList[i].quenstion.contains("T")) {
                ++totalT
            } else if (selectList[i].quenstion.contains("F")) {
                ++totalF
            } else if (selectList[i].quenstion.contains("P")) {
                ++totalP
            } else if (selectList[i].quenstion.contains("J")) {
                ++totalJ
            }
        }

    }

    private fun calculator(){
        val mbtiScores = mapOf(
            "E" to totalE,
            "I" to totalI,
            "S" to totalS,
            "N" to totalN,
            "T" to totalT,
            "F" to totalF,
            "P" to totalP,
            "J" to totalJ
        )
        val mbtiCalculationRequest = MbtiCalculationRequest(mbtiScores)
        val callPost = RetrofitClient.apiService.postMbtiCalculation(mbtiCalculationRequest)
        callPost.enqueue(object : Callback<MbtiCalculationResponse> {
            override fun onResponse(call: Call<MbtiCalculationResponse>, response: Response<MbtiCalculationResponse>) {
                if (response.isSuccessful) {
                    val mbtiCalculationResponse = response.body()
                    val characterTitle = mbtiCalculationResponse!!.name
                    val characterExplain = mbtiCalculationResponse.description
                    val characterType = mbtiCalculationResponse.type

                    completeCharacter(characterTitle, characterExplain, characterType)

                } else {
                    Toast.makeText(context,"서버 에러",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MbtiCalculationResponse>, t: Throwable) {
                Toast.makeText(context,"네트워크 에러",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun completeCharacter(characterTitle:String, characterExplain : String, characterType :String){
        binding!!.qWrite.visibility =View.GONE
        binding!!.qCheck.visibility = View.GONE
        binding!!.tvQuestion.visibility = View.GONE
        binding!!.gridView.visibility = View.GONE
        binding!!.ivBack.visibility = View.GONE
        binding!!.llCompleteCharacter.visibility = View.VISIBLE

        binding!!.tvTitleQ.text = editName!!.text
        binding!!.tvCharacterTitle.text = characterTitle
        binding!!.tvCharacterExplain.text = characterExplain

        when (characterType) {
            "ENFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_enfj)
            }
            "ENFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_enfp)
            }
            "ENTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_entj)
            }
            "ENTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_entp)
            }
            "ESFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_esfj)
            }
            "ESFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_esfp)
            }
            "ESTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_estj)
            }
            "ESTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_estp)
            }
            "INFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_infj)
            }
            "INFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_infp)
            }
            "INTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_intj)
            }
            "INTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_intp)
            }
            "ISFJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_isfj)
            }
            "ISFP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_isfp)
            }
            "ISTJ" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_istj)
            }
            "ISTP" -> {
                binding!!.ivCharacter.setImageResource(R.drawable.property_2_istp)
            }
        }


    }
    private fun getService() : ShopService{
        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.201.67.203:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ShopService::class.java)
    }

}

