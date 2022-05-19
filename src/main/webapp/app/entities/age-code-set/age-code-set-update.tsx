import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAgeCodeSet } from 'app/shared/model/age-code-set.model';
import { getEntity, updateEntity, createEntity, reset } from './age-code-set.reducer';

export const AgeCodeSetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ageCodeSetEntity = useAppSelector(state => state.ageCodeSet.entity);
  const loading = useAppSelector(state => state.ageCodeSet.loading);
  const updating = useAppSelector(state => state.ageCodeSet.updating);
  const updateSuccess = useAppSelector(state => state.ageCodeSet.updateSuccess);
  const handleClose = () => {
    props.history.push('/age-code-set' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ageCodeSetEntity,
      ...values,
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
          ...ageCodeSetEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="csc2022App.ageCodeSet.home.createOrEditLabel" data-cy="AgeCodeSetCreateUpdateHeading">
            <Translate contentKey="csc2022App.ageCodeSet.home.createOrEditLabel">Create or edit a AgeCodeSet</Translate>
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
                  id="age-code-set-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('csc2022App.ageCodeSet.code')}
                id="age-code-set-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.ageCodeSet.codeId')}
                id="age-code-set-codeId"
                name="codeId"
                data-cy="codeId"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.ageCodeSet.labelEn')}
                id="age-code-set-labelEn"
                name="labelEn"
                data-cy="labelEn"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.ageCodeSet.labelFi')}
                id="age-code-set-labelFi"
                name="labelFi"
                data-cy="labelFi"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.ageCodeSet.labelSv')}
                id="age-code-set-labelSv"
                name="labelSv"
                data-cy="labelSv"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/age-code-set" replace color="info">
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

export default AgeCodeSetUpdate;
