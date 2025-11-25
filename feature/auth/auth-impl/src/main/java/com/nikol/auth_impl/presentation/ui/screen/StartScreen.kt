package com.nikol.auth_impl.presentation.ui.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nikol.auth_impl.presentation.mvi.effect.StartPageEffect
import com.nikol.auth_impl.presentation.mvi.intent.StartPageIntent
import com.nikol.auth_impl.presentation.viewModel.StartPageRouter
import com.nikol.auth_impl.presentation.viewModel.StartPageViewModel
import com.nikol.di.scope.viewModelWithRouter
import io.ktor.utils.io.InternalAPI
import com.nikol.auth_impl.presentation.mvi.state.CreateSessionState // Импортируйте ваш state

@OptIn(InternalAPI::class)
@Composable
fun StartScreen(
    onMain: () -> Unit,
) {
    val viewModel = viewModelWithRouter<StartPageViewModel, StartPageRouter> {
        object : StartPageRouter {
            override fun main() = onMain()
        }
    }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val intent =
        remember { Intent(Intent.ACTION_VIEW, "https://www.themoviedb.org/signup".toUri()) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                StartPageEffect.GoToBrowser -> {
                    context.startActivity(intent)
                }
            }
        }
    }

    StartScreenContent(
        login = uiState.login,
        password = uiState.password,
        loginState = uiState.sessionState,
        guestState = uiState.guestButtonState,
        isPasswordVisible = uiState.showPassword,
        onLoginChange = { viewModel.setIntent(StartPageIntent.ChangeLogin(it)) },
        onPasswordChange = { viewModel.setIntent(StartPageIntent.ChangePassword(it)) },
        onLoginClick = { viewModel.setIntent(StartPageIntent.LogIn) },
        onGuestClick = { viewModel.setIntent(StartPageIntent.ContinueWithGuestAccount) },
        onSignUpClick = { viewModel.setIntent(StartPageIntent.CreateAccount) },
        switchPasswordVisibility = { viewModel.setIntent(StartPageIntent.SwitchPasswordVisibility) }
    )
}

@Composable
fun StartScreenContent(
    login: String,
    password: String,
    loginState: CreateSessionState,
    guestState: CreateSessionState,
    isPasswordVisible: Boolean,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onGuestClick: () -> Unit,
    onSignUpClick: () -> Unit,
    switchPasswordVisibility: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val isLoginLoading = loginState is CreateSessionState.Loading
    val isGuestLoading = guestState is CreateSessionState.Loading
    val isAnyLoading = isLoginLoading || isGuestLoading

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Please sign in to continue",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            OutlinedTextField(
                value = login,
                onValueChange = onLoginChange,
                label = { Text("Username") },
                enabled = !isAnyLoading,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                enabled = !isAnyLoading,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    val image =
                        if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(
                        onClick = { switchPasswordVisibility() },
                        enabled = !isAnyLoading
                    ) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    if (!isAnyLoading) onLoginClick()
                })
            )

            if (loginState is CreateSessionState.Error) {
                Text(
                    text = "Login failed. Please check your credentials.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.Start)
                )
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    onLoginClick()
                },
                enabled = !isAnyLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (isLoginLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Login", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Text(
                    text = "Or continue with",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            Spacer(Modifier.height(24.dp))

            OutlinedButton(
                onClick = onGuestClick,
                enabled = !isAnyLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
            ) {
                if (isGuestLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Guest Mode", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (isAnyLoading) Color.Gray else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(enabled = !isAnyLoading) { onSignUpClick() }
                        .padding(4.dp)
                )
            }
        }
    }
}