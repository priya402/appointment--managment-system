from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import Feedback
from .serialiser import FeedbackSerializer

# View to get all feedback
@api_view(['GET'])
def get_all_feedback(request):
    feedbacks = Feedback.objects.all()  # Get all feedback
    serializer = FeedbackSerializer(feedbacks, many=True)  # Serialize the data
    return Response(serializer.data)  # Return serialized feedbacks

# View to get feedback by user ID
@api_view(['GET'])
def get_feedback_by_user_id(request, user_id):
    try:
        feedback = Feedback.objects.filter(userId=user_id)  # Filter feedback by user ID
        if feedback.exists():
            serializer = FeedbackSerializer(feedback, many=True)  # Serialize the data
            return Response(serializer.data)  # Return serialized feedbacks
        else:
            return Response({"detail": "Feedback not found for this user."}, status=status.HTTP_404_NOT_FOUND)
    except Feedback.DoesNotExist:
        return Response({"detail": "Feedback not found."}, status=status.HTTP_404_NOT_FOUND)

# Submit feedback (POST method as previously defined)
@api_view(['POST'])
def submit_feedback(request):
    if request.method == 'POST':
        serializer = FeedbackSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
