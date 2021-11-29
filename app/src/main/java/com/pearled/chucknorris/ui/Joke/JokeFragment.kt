package com.pearled.chucknorris.ui.Joke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.pearled.chucknorris.R
import com.pearled.chucknorris.databinding.FragmentJokeBinding
import com.pearled.chucknorris.databinding.FragmentListBinding
import com.pearled.chucknorris.ui.MainActivity
import com.pearled.chucknorris.ui.ToolbarTitleListener
import com.pearled.chucknorris.utils.ChuckResult

private const val ARG_CATEGORY = "category"

class JokeFragment : Fragment() {
    private var category: String? = null
    private var _binding: FragmentJokeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category?.let { category ->
            (requireActivity() as ToolbarTitleListener).updateTitle(category.uppercase())
            viewModel.updateCategory(category)
        }
        binding.jokeButtonMore.setOnClickListener {
            viewModel.fetchRandomJoke()
        }
        viewModel.joke.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ChuckResult.Loading -> {
                    binding.progessBar.visibility = View.VISIBLE
                }
                is ChuckResult.Failure -> {
                    binding.progessBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
                is ChuckResult.Success -> {
                    binding.progessBar.visibility = View.GONE
                    binding.jokeText.text = result.value.text
                    if (!result.value.iconUrl.isBlank()) {
                        Glide.with(binding.root)
                            .load(result.value.iconUrl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                            .into(binding.jokeIcon)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(category: String) =
            JokeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}