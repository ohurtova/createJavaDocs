package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.comment.CommentDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * This class manage comment endpoint that returns from API calls.
 */
public class CommentEndpoint extends AbstractWebEndpoint {
    // Logger

    private static final Logger LOGGER = LogManager.getLogger();

    // Data for response
    private static final String COMMENTS_END = "/comments";
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    /**
     * Constructor
     *
     * @param specification - type of request you will use
     */
    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Creates CommentDto
     *
     * @param commentDto - CommentDto object that contains data for comment creating
     * @return - created CommentDto
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED)
                .extract().as(CommentDto.class);
    }

    /**
     * Takes CommentDto and verifies status code of this object
     *
     * @param commentDto - early created CommentDto object
     * @param status     - expected status code for updating
     * @return - ValidatableResponse object
     */
    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(
                this.specification,
                COMMENTS_END,
                commentDto)
                .statusCode(status.getCode());
    }

    /**
     * Updates existing CommentDto with new id
     *
     * @param id         - new id
     * @param commentDto - existing CommentDto object
     * @return - updated CommentDto object
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Updates existing ValidatableResponse with new id
     *
     * @param commentDto - existing CommentDto object
     * @param id         - new id
     * @param status     - expected status code for updating
     * @return - updated ValidatableResponse
     */
    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(
                this.specification,
                COMMENTS_RESOURCE_END,
                commentDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Gets CommentDto by id
     *
     * @param id - id for CommentDto searching
     * @return - CommentDto that was found by id
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Gets ValidatableResponse by id
     *
     * @param id     - id for ValidatableResponse searching
     * @param status - expected status code for getting ValidatableResponse by id
     * @return - ValidatableResponse that was found by id
     */
    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(
                this.specification,
                COMMENTS_RESOURCE_END,
                String.valueOf(id))
                .statusCode(status.getCode());
    }

    /**
     * Gets all existing CommentDto objects
     *
     * @return - list of all existing CommentDto objects
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    /**
     * Gets all existing ValidatableResponse objects
     *
     * @return - list of all existing ValidatableResponse objects
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
