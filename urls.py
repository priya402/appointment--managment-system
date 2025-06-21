from django.urls import path
from . import views

urlpatterns = [
    path('submit-feedback/', views.submit_feedback, name='submit-feedback'),
     path('feedback/', views.get_all_feedback, name='get_all_feedback'),
    path('feedback/<str:user_id>/', views.get_feedback_by_user_id, name='get_feedback_by_user_id'),
]
