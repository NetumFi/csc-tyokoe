import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ISearchSetting } from 'app/shared/model/search-setting.model';
import { getEntity, updateEntity, createEntity, reset } from './search-setting.reducer';

export const SearchSettingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const searchSettingEntity = useAppSelector(state => state.searchSetting.entity);
  const loading = useAppSelector(state => state.searchSetting.loading);
  const updating = useAppSelector(state => state.searchSetting.updating);
  const updateSuccess = useAppSelector(state => state.searchSetting.updateSuccess);
  const handleClose = () => {
    props.history.push('/search-setting' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...searchSettingEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...searchSettingEntity,
          user: searchSettingEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="csc2022App.searchSetting.home.createOrEditLabel" data-cy="SearchSettingCreateUpdateHeading">
            <Translate contentKey="csc2022App.searchSetting.home.createOrEditLabel">Create or edit a SearchSetting</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="search-setting-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('csc2022App.searchSetting.searchTerm')}
                id="search-setting-searchTerm"
                name="searchTerm"
                data-cy="searchTerm"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.searchSetting.educationLevel')}
                id="search-setting-educationLevel"
                name="educationLevel"
                data-cy="educationLevel"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.searchSetting.role')}
                id="search-setting-role"
                name="role"
                data-cy="role"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.searchSetting.age')}
                id="search-setting-age"
                name="age"
                data-cy="age"
                type="text"
              />
              <ValidatedField
                id="search-setting-user"
                name="user"
                data-cy="user"
                label={translate('csc2022App.searchSetting.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/search-setting" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SearchSettingUpdate;
