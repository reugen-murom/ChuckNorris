package com.pearled.chucknorris.ui.List

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearled.chucknorris.databinding.FragmentListBinding
import com.pearled.chucknorris.utils.ChuckResult

class ListFragment : Fragment(), OnCategoryClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()
    private var categoryAdapter: CategoryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryAdapter = CategoryListAdapter(this)
        binding.listRecyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
        }
        viewModel.categoryList.observe(viewLifecycleOwner) { result ->
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
                    categoryAdapter?.submitList(result.value)
                }
            }
        }
    }

    override fun onCategoryClick(category: String) {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToJokeFragment(category)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}