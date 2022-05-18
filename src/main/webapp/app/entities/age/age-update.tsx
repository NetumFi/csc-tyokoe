import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAge } from 'app/shared/model/age.model';
import { getEntity, updateEntity, createEntity, reset } from './age.reducer';

export const AgeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ageEntity = useAppSelector(state => state.age.entity);
  const loading = useAppSelector(state => state.age.loading);
  const updating = useAppSelector(state => state.age.updating);
  const updateSuccess = useAppSelector(state => state.age.updateSuccess);
  const handleClose = () => {
    props.history.push('/age' + props.location.search);
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
      ...ageEntity,
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
          ...ageEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="csc2022App.age.home.createOrEditLabel" data-cy="AgeCreateUpdateHeading">
            <Translate contentKey="csc2022App.age.home.createOrEditLabel">Create or edit a Age</Translate>
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
                  id="age-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('csc2022App.age.code')} id="age-code" name="code" data-cy="code" type="text" />
              <ValidatedField label={translate('csc2022App.age.codeId')} id="age-codeId" name="codeId" data-cy="codeId" type="text" />
              <ValidatedField label={translate('csc2022App.age.labelEn')} id="age-labelEn" name="labelEn" data-cy="labelEn" type="text" />
              <ValidatedField label={translate('csc2022App.age.labelFi')} id="age-labelFi" name="labelFi" data-cy="labelFi" type="text" />
              <ValidatedField label={translate('csc2022App.age.labelSv')} id="age-labelSv" name="labelSv" data-cy="labelSv" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/age" replace color="info">
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

export default AgeUpdate;
