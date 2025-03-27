import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentUserAccountBinding
import com.example.newsapplication.presentation.ui.fragment.BooksMarkFragment
import com.example.newsapplication.presentation.ui.fragment.NewsFragment
import com.example.newsapplication.presentation.viewmodel.UserAccountViewModel

class UserAccount : Fragment() {
    private var _binding: FragmentUserAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserAccountViewModel::class.java)

        viewModel.loadUserData(requireContext())

        viewModel.email.observe(viewLifecycleOwner) { email ->
            binding.userEmail.text = email
        }

        viewModel.login.observe(viewLifecycleOwner) { login ->
            binding.userLogin.text = login
        }

        binding.btnHome.setOnClickListener {
            toNewsScreen()
        }

        binding.btnBookmarks.setOnClickListener {
            toBookmarksScreen()
        }
    }

    private fun toNewsScreen() {
        val newsFragment = NewsFragment()
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, newsFragment)
            .addToBackStack(null).commit()
    }

    private fun toBookmarksScreen() {
        val bookmarksFragment= BooksMarkFragment()
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, bookmarksFragment)
            .addToBackStack(null).commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}