package br.com.task_manager_api.services;

import br.com.task_manager_api.models.Subscription;
import br.com.task_manager_api.repositories.SubscriptionRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubscriptionService {

  private final SubscriptionRepository repository;
  //private final FirebaseMessaging fcm;

  public Subscription save(Subscription subscription) {//throws FirebaseMessagingException {
    //Part of option 1:
    //fcm.subscribeToTopic(Collections.singletonList(subscription.getToken()), subscription.getTask().getUuid().toString());
    return this.repository.save(subscription);
  }

  public List<Subscription> findAll() {
    return this.repository.findAll();
  }

  public Optional<Subscription> findById(UUID id) {
    return this.repository.findById(id);
  }

  public List<Subscription> findBy(Subscription subscription) {
    return this.repository.findAll(Example.of(subscription));
  }

  public Page<Subscription> findBy(Subscription subscription, Pageable pageable) {
    return this.repository.findAll(Example.of(subscription), pageable);
  }

  public void deleteAll() {
    this.repository.deleteAll();
  }

  public void deleteById(UUID id) {
    this.repository.deleteById(id);
  }

  public void delete(Subscription subscription) {//throws FirebaseMessagingException {
    //Part of option 1:
    //fcm.unsubscribeFromTopic(Collections.singletonList(subscription.getToken()), subscription.getTask().getUuid().toString());
    this.repository.delete(subscription);
  }
}
