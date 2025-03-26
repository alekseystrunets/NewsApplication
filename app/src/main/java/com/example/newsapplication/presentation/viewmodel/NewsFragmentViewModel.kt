import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.api.RetrofitClient
import com.example.newsapplication.presentation.NewsItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Locale

class NewsFragmentViewModel : ViewModel() {
    private val _newsList = MutableLiveData<List<NewsItem>>()
    val newsList: LiveData<List<NewsItem>> = _newsList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadNews(category: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                val response = when (category) {
                    "Apple" -> RetrofitClient.api.getAppleNews()
                    "Tesla" -> RetrofitClient.api.getTeslaNews()
                    "US business" -> RetrofitClient.api.getUSBusinessHeadlines()
                    "TechCrunch" -> RetrofitClient.api.getTechCrunchHeadlines()
                    "Wall Street Journal" -> RetrofitClient.api.getWSJArticles()
                    else -> throw IllegalArgumentException("Unknown category")
                }

                if (response.articles.isEmpty()) {
                    _error.postValue("No articles found for $category")
                } else {
                    _newsList.postValue(response.articles.map { article ->
                        NewsItem(
                            title = article.title,
                            author = article.author ?: "Unknown",
                            date = formatDate(article.publishedAt),
                            imageUrl = article.urlToImage,
                            content = article.content ?: "",
                            source = article.source.name,
                            articleUrl = article.url
                        )
                    })
                }
            } catch (e: HttpException) {
                val errorMsg = when (e.code()) {
                    403 -> "API key invalid or quota exceeded"
                    429 -> "Too many requests"
                    else -> "API error: ${e.code()}"
                }
                _error.postValue("$category: $errorMsg")
                Log.e("NewsAPI", "HTTP Error loading $category: $errorMsg")
            } catch (e: Exception) {
                _error.postValue("$category: ${e.message}")
                Log.e("NewsAPI", "Error loading $category", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun formatDate(dateString: String?): String {
        if (dateString == null) return "Date not available"

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "Invalid date"
        }
    }
}