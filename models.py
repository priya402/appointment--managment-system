
from django.db import models

class Feedback(models.Model):
    FEEDBACK_TYPE_CHOICES = [
        ('complaint', 'Complaint'),
        ('good', 'Good Response'),
    ]

    feedback_type = models.CharField(max_length=20, choices=FEEDBACK_TYPE_CHOICES)
    rating = models.PositiveIntegerField()
    feedback_text = models.TextField(blank=True, null=True)
    created_at = models.DateTimeField(auto_now_add=True)
    user_id=models.TextField(blank=False, null=True)

    def __str__(self):
        return f'{self.feedback_type} - {self.rating}'
