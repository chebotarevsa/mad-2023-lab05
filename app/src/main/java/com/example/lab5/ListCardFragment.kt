package com.example.lab5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.databinding.FragmentListCardBinding


class ListCardFragment : Fragment() {
    private var _binding: FragmentListCardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CustomRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCardBinding.inflate(layoutInflater, container, false)

        val cards = Model.cards

        val recyclerView: RecyclerView = binding.recyclerid
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CustomRecyclerAdapter(cards) {
            val action = ListCardFragmentDirections.actionListCardFragmentToSeeCardFragment()
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshCardsViewWith(Model.cards)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListCardFragment().apply {
                arguments = Bundle()
            }
    }
}