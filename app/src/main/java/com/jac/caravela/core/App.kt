package com.jac.caravela.core

import android.app.Application
import android.content.Context
import android.content.Intent
import com.jac.caravela.model.User
import com.jac.caravela.persistence.DataBaseClient
import com.jac.caravela.repositories.*
import com.jac.caravela.scenes.login.LoginActivity
import com.jac.caravela.service.ApiClient

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val database = DataBaseClient.buildDatabase(this) // criando banco de dados -- database é a unica instancia
        val api = ApiClient() // crianddo uma instancia de ApiClient
        setupRepositories(database, api)

        hasLoggedUser()
    }

    private fun setupRepositories(database: DataBaseClient, apiClient: ApiClient) {
        userRepository = UserRepository(database.userDAO(), apiClient.userApi)
        courseRepository = CourseRepository(apiClient.courseApi)
        classRepository = ClassRepository(apiClient.classApi)
        eventRepository = EventRepository(apiClient.eventApi)
        messageRepository = MessageRepository(apiClient.messageApi)
        chatRepository = ChatRepository(apiClient.chatApi)
        enrollmentRepository = EnrollmentRepository(apiClient.enrollmentApi)
        postRepository = PostRepository(apiClient.postApi)
        partnerRepository = PartnerRepository(apiClient.partnerApi)
        paymentRepository = PaymentRepository(apiClient.paymentApi)

    }

    private fun hasLoggedUser() {
        user = userRepository.getLoggedUser()
    }

    companion object {
        const val REQ_CODE = 1

        var user: User? = null

        lateinit var userRepository: UserRepository
            private set

        lateinit var courseRepository: CourseRepository
            private set

        lateinit var classRepository: ClassRepository
            private set

        lateinit var eventRepository: EventRepository
            private set

        lateinit var messageRepository: MessageRepository
            private set

        lateinit var chatRepository: ChatRepository
            private set

        lateinit var enrollmentRepository: EnrollmentRepository
            private set

        lateinit var postRepository: PostRepository
            private set

        lateinit var partnerRepository: PartnerRepository
            private set

        lateinit var paymentRepository: PaymentRepository
            private set

        fun restartApp(context: Context) {
            userRepository.logout()
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
}