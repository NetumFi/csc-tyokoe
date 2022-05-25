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
import { IEducationLevelCodeSet } from 'app/shared/model/education-level-code-set.model';
import { getEntities as getEducationLevelCodeSets } from 'app/entities/education-level-code-set/education-level-code-set.reducer';
import { IAgeCodeSet } from 'app/shared/model/age-code-set.model';
import { getEntities as getAgeCodeSets } from 'app/entities/age-code-set/age-code-set.reducer';
import { ISearchSetting } from 'app/shared/model/search-setting.model';
import { getEntity, updateEntity, createEntity, reset } from './search-setting.reducer';

export const SearchSettingUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const educationLevelCodeSets = useAppSelector(state => state.educationLevelCodeSet.entities);
  const ageCodeSets = useAppSelector(state => state.ageCodeSet.entities);
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
    dispatch(getEducationLevelCodeSets({}));
    dispatch(getAgeCodeSets({}));
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
      educationLevelCodeSet: educationLevelCodeSets.find(it => it.id.toString() === values.educationLevelCodeSet.toString()),
      ageCodeSet: ageCodeSets.find(it => it.id.toString() === values.ageCodeSet.toString()),
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
          educationLevelCodeSet: searchSettingEntity?.educationLevelCodeSet?.id,
          ageCodeSet: searchSettingEntity?.ageCodeSet?.id,
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
                label={translate('csc2022App.searchSetting.fieldOfStudy')}
                id="search-setting-fieldOfStudy"
                name="fieldOfStudy"
                data-cy="fieldOfStudy"
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
              <ValidatedField
                id="search-setting-educationLevelCodeSet"
                name="educationLevelCodeSet"
                data-cy="educationLevelCodeSet"
                label={translate('csc2022App.searchSetting.educationLevelCodeSet')}
                type="select"
              >
                <option value="" key="0" />
                {educationLevelCodeSets
                  ? educationLevelCodeSets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.labelFi}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="search-setting-ageCodeSet"
                name="ageCodeSet"
                data-cy="ageCodeSet"
                label={translate('csc2022App.searchSetting.ageCodeSet')}
                type="select"
              >
                <option value="" key="0" />
                {ageCodeSets
                  ? ageCodeSets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.labelFi}
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
