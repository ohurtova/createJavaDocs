package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.user.UserDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * This class manage user endpoint that returns from API calls.
 */
public class UserEndpoint extends AbstractWebEndpoint {

    // Logger
    private static final Logger LOGGER = LogManager.getLogger();

    // Data for response
    private static final String USERS_END = "/users";
    private static final String USERS_RESOURCE_END = "/users/{userID}";

    /**
     * Constructor
     *
     * @param specification - type of request you will use
     */
    public UserEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates CommentDto
     *
     * @param userDto - UserDto object that contains data for user creating
     * @return - created UserDto
     */
    public UserDto create(UserDto userDto) {
        return create(userDto, HttpStatus.CREATED)
                .extract().as(UserDto.class);
    }

    /**
     * Takes UserDto and verifies status code of this object
     *
     * @param userDto - early created UserDto object
     * @param status  - expected status code for updating
     * @return - ValidatableResponse object
     */
    public ValidatableResponse create(UserDto userDto, HttpStatus status) {
        LOGGER.info("Create new User");
        return post(
                this.specification,
                USERS_END,
                userDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates existing UserDto with new id
     *
     * @param id      - new id
     * @param userDto - existing CommentDto object
     * @return - updated UserDto object
     */
    public UserDto update(int id, UserDto userDto) {
        return update(userDto, id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Updates existing ValidatableResponse with new id
     *
     * @param userDto - existing UserDto object
     * @param id      - new id
     * @param status  - expected status code for updating
     * @return - updated ValidatableResponse
     */
    public ValidatableResponse update(UserDto userDto, int id, HttpStatus status) {
        LOGGER.info("Update User by id [{}]", id);
        return put(
                this.specification,
                USERS_RESOURCE_END,
                userDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Gets UserDto by id
     *
     * @param id - id for UserDto searching
     * @return - UserDto that was found by id
     */
    public UserDto getById(String id) {
        return getById(id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Gets ValidatableResponse by id
     *
     * @param id     - id for ValidatableResponse searching
     * @param status - expected status code for getting ValidatableResponse by id
     * @return - ValidatableResponse that was found by id
     */
    public ValidatableResponse getById(String id, HttpStatus status) {
        LOGGER.info("Get User by id [{}]", id);
        return get(
                this.specification,
                USERS_RESOURCE_END,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Gets all existing UserDto objects
     *
     * @return - list of all existing UserDto objects
     */
    public List<UserDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(UserDto[].class));
    }

    /**
     * Gets all existing ValidatableResponse objects
     *
     * @return - list of all existing ValidatableResponse objects
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Users");
        ValidatableResponse response = get(this.specification, USERS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
