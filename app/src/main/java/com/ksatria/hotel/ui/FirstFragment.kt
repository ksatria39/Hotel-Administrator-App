package com.ksatria.hotel.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksatria.hotel.R
import com.ksatria.hotel.application.HotelApp
import com.ksatria.hotel.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val hotelViewModel: HotelViewModel by viewModels {
        HotelViewModelFactory((applicationContext as HotelApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HotelListAdapter{ hotel ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(hotel)
            findNavController().navigate(action)
        }
        binding.rvData.adapter = adapter
        binding.rvData.layoutManager = LinearLayoutManager(context)
        // mengecek apakah ada hotel dalam daftar hotel
        // jika tidak ada maka ilustrasi dan teks perinatan error akan tampil
        // jika ada maka ilustrasi dan peringatan tidak tampil
        hotelViewModel.allHotels.observe(viewLifecycleOwner){hotels ->
            hotels.let {
                if(hotels.isEmpty()){
                    binding.ivError.visibility = View.VISIBLE
                    binding.tvError.visibility = View.VISIBLE
                }else{
                    binding.ivError.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                adapter.submitList(hotels)
            }
        }

        // jika diklik akan diarahkan ke fragment 2 yg merupakan halaman tambah hotel
        binding.fabAdd.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}