package se.pj.tbike.http.model.category;

import com.ank.japi.exception.HttpException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryRequest {

    private final Long id;

    private final String name;

    private final String imageUrl;

    private final String description;

    public String getName() {
        if (name == null) {
            throw HttpException.badRequest(
                    "Name is required."
            );
        }
        if (name.isBlank()) {
            throw HttpException.badRequest(
                    "Name cannot be empty."
            );
        }
        if (name.length() > 300) {
            throw HttpException.badRequest(
                    "Name is too long (400 characters)."
            );
        }
        return name;
    }

    public String getImageUrl() {
        if (imageUrl == null) {
            throw HttpException.badRequest(
                    "Image's url is required."
            );
        }
        if (imageUrl.isBlank()) {
            throw HttpException.badRequest(
                    "Image's url cannot be empty."
            );
        }
        return imageUrl;
    }

    public String getDescription() {
        if (description != null && description.isBlank()) {
            throw HttpException.badRequest(
                    "Description cannot be empty."
            );
        }
        return description;
    }

}
