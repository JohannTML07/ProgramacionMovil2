package com.example.evaluacion1.Screens

//import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.evaluacion1.SicenetApp
import com.example.evaluacion1.data.SiceRepositotory
import com.example.evaluacion1.model.Estudiante
import com.example.evaluacion1.model.Respuesta
import com.example.evaluacion1.model.Usuario
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.IOException


sealed interface UserUiState{
    data class Success(val userAccess: Response<Respuesta>) : UserUiState

    object  Error : UserUiState

    object  Laoding : UserUiState
}
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ViewModelLogin(private val repository: SiceRepositotory): ViewModel() {
    var Usuario by mutableStateOf("")
        private set
    var Password by mutableStateOf("")
        private set
    var Estado by mutableStateOf(LoginState())
        private set
    fun updateUsuario(usuario: String){
        Usuario = usuario
    }
    fun updatePasword(pasword: String){
        Password = pasword
    }
    fun updateU(estado: LoginState){
        Estado = estado
    }

    //Llamada a la conexion

    var userUIState: UserUiState by mutableStateOf(UserUiState.Laoding)
        private set


    fun Authentication(/*n: String, m: String*/) {
        GlobalScope.launch {
            try {
                updateU(Estado.copy(usuario = repository.Acceso(Usuario, Password)))
                if (Estado.usuario.acceso){
                    updateU(Estado.copy(estudiante = repository.infoMatricula()))
                }
            }
            catch (e: Exception){
                Log.d("Error","Error en la autenticación")
            }

        }
    }

    companion object{
        @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
        val Factory: ViewModelProvider.Factory= viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SicenetApp)
                val siceRepository =  application.container.siceRepositotory
                ViewModelLogin(
                    repository = siceRepository
                )
            }
        }
    }
}

data class LoginState(
    val usuario: Usuario = Usuario(false, "", 0, "", ""),
    val estudiante: Estudiante = Estudiante(
        fechaReins = "",
        modEducativo = 0,
        adeudo = false,
        urlFoto = "",
        adeudoDescripcion = "",
        inscrito = false,
        estatus = "",
        semActual = 0,
        cdtosAcumulados = 0,
        cdtosActuales = 0,
        especialidad = "",
        carrera = "",
        lineamiento = 0,
        nombre = "",
        matricula = ""
    )
)