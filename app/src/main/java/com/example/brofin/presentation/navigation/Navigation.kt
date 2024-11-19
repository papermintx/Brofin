package com.example.brofin.presentation.navigation

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.brofin.presentation.authentication.login.LoginScreen
import com.example.brofin.presentation.authentication.register.RegisterScreen
import com.example.brofin.presentation.camera.CameraScreen
import com.example.brofin.presentation.expenses.AddExpenses
import com.example.brofin.presentation.main.HomeApp
import com.example.brofin.presentation.main.budget.BudgetDetailScreen
import com.example.brofin.presentation.main.budget.components.CreateBudgetScreen
import com.example.brofin.presentation.setup.SetupIncome
import com.example.brofin.presentation.splash.SplashScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Navigation() {

    val navController = rememberNavController()

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        photoUri = uri
    }

    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route
    ){
        composable(
            route = NavScreen.SetupIncome.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            SetupIncome()
        }
        composable(
            route = NavScreen.Login.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            LoginScreen(
                goToRegister = {
                    navController.navigate(NavScreen.Register.route)
                },
                goHome = {
                    navController.navigate(NavScreen.Home.route){
                        popUpTo(NavScreen.Login.route){
                            inclusive = true
                        }
                    }
                },
                goSetupIncome = {
                    navController.navigate(NavScreen.SetupIncome.route)
                }
            )
        }
        composable(
            route = NavScreen.Home.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            HomeApp(
                goLogin = {
                    navController.navigate(NavScreen.Login.route){
                        popUpTo(NavScreen.Home.route){
                            inclusive = true
                        }
                    }
                },
                goCreateBudget = {
                    navController.navigate(NavScreen.CreateBudgetingDiary.route)
                },
                goCreateDiary = {
                    navController.navigate(NavScreen.AddDiaryBudget.route)
                }
            )
        }
        composable(
            route = NavScreen.Register.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ) {
            RegisterScreen(
                goBack = {
                    navController.popBackStack()
                },
                goHome = {
                    navController.navigate(NavScreen.Home.route) {
                        popUpTo(NavScreen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                goSetupIncome = {
                        navController.navigate(NavScreen.SetupIncome.route)
                }
            )
        }
        composable(
            route = NavScreen.Splash.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            SplashScreen(
                goHome = {
                    navController.navigate(NavScreen.Home.route){
                        popUpTo(NavScreen.Splash.route){
                            inclusive = true
                        }
                    }
                },
                goLogin = {
                    navController.navigate(NavScreen.Login.route) {
                        popUpTo(NavScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                goSetupIncome = {
                    navController.navigate(NavScreen.SetupIncome.route) {
                        popUpTo(NavScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = NavScreen.Camera.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            CameraScreen(
                currentBackstackEntry = { uri ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(NavArgument.PHOTOURI, uri)
                },
                goBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            route = NavScreen.CreateBudgetingDiary.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            CreateBudgetScreen {}
        }
        composable(
            route = NavScreen.AddDiaryBudget.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ){
            AddExpenses(
                photoUri = photoUri,
                onBackClick = {
                    navController.popBackStack()
                },
                currentBackStack = {
                    navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Uri?>(NavArgument.PHOTOURI)?.observeForever { uri ->
                        photoUri = uri
                    }
                },
                goGallery = {
                    launcher.launch("image/*")
                },
                goCamera = {
                    navController.navigate(NavScreen.Camera.route)
                },
                goback = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = NavScreen.DetailBudget.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -1000 },
                    animationSpec = tween(durationMillis = 500)
                ) + fadeOut(animationSpec = tween(durationMillis = 500))
            },
            arguments = listOf(navArgument(NavArgument.ID) { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(NavArgument.ID)

            BudgetDetailScreen(
                onBackClick = { /* TODO: Navigasi kembali */ },
                onDeleteClick = { /* TODO: Aksi hapus */ },
                onEditClick = { /* TODO: Aksi edit */ },
                categoryName = "Shopping", // Ganti dengan data sesuai `id` jika tersedia
                remainingAmount = "$0",    // Sesuaikan data berdasarkan `id`
                color = Color.Gray.toArgb().toString(),        // Warna default atau sesuaikan
                icon = Icons.Default.ShoppingCart, // Ikon atau gambar sesuai kategori
                isOverLimit = true,        // Flag jika melebihi anggaran
                progressColor = Color.Red, // Warna progress
                progress = 1f              // Nilai progress
            )
        }

    }
}