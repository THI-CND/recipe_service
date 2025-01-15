package de.benedikt_schwering.thicnd.adapters.out.redis;

import de.benedikt_schwering.thicnd.adapters.out.redis.dto.IngredientCache;
import de.benedikt_schwering.thicnd.domain.model.Ingredient;
import de.benedikt_schwering.thicnd.ports.out.IngredientCacheProvider;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisIngredientCacheProviderImpl implements IngredientCacheProvider {
    private final RedisTemplate<Long, IngredientCache> template;

    public RedisIngredientCacheProviderImpl(RedisTemplate<Long, IngredientCache> template) {
        this.template = template;
    }

    @Override
    public void cacheIngredient(Ingredient ingredient) {
        template.opsForValue().set(
                ingredient.getId(),
                IngredientCache.fromIngredient(ingredient),
                5,
                TimeUnit.MINUTES
        );
    }

    @Override
    public Optional<Ingredient> getCachedIngredient(long id) {
        var cachedIngredient = template.opsForValue().get(id);

        if (cachedIngredient == null)
            return Optional.empty();

        return Optional.of(cachedIngredient.toIngredient());
    }
}
