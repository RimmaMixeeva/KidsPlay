package rimma.mixeeva.kidsplay.navigation

import androidx.navigation.NavHostController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    var navController: NavHostController? = null

    fun setController(controller: NavHostController){
        navController = controller
    }
    fun clear(){
        navController = null
    }
    fun navigate(screen: Screen){
        navController?.navigate(screen)
    }
    fun popBackStack(){
        navController?.popBackStack()
    }
    fun navigateToNextAndDeletePreviousScreen(current: Screen, next: Screen){
        navController?.navigate(next) {
            popUpTo(current) { inclusive = true }
        }
    }

}