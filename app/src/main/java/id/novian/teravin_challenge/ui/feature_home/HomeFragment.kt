package id.novian.teravin_challenge.ui.feature_home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.novian.teravin_challenge.R
import id.novian.teravin_challenge.base.BaseFragment
import id.novian.teravin_challenge.databinding.FragmentHomeBinding
import id.novian.teravin_challenge.util.UiState
import id.novian.teravin_challenge.util.showToast

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun setup() {
        viewModel.getAllPopularMovies()

        checkUiState()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        movieAdapter = MovieAdapter()

        binding.rvPopularMovie.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun checkUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Loading -> {
                    binding.circularProgressBar.visibility = View.VISIBLE
                    binding.rvPopularMovie.visibility = View.GONE
                }
                is UiState.Failure -> {
                    showSnackbar(it.message)
                }
                is UiState.Success -> {
                    movieAdapter.submitList(it.data)
                    binding.circularProgressBar.visibility = View.GONE
                    binding.rvPopularMovie.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        val snack = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        snack.setAction("Tutup") {
            snack.dismiss()
        }
        snack.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}