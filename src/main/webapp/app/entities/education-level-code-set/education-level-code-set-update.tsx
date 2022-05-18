import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEducationLevelCodeSet } from 'app/shared/model/education-level-code-set.model';
import { getEntity, updateEntity, createEntity, reset } from './education-level-code-set.reducer';

export const EducationLevelCodeSetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const educationLevelCodeSetEntity = useAppSelector(state => state.educationLevelCodeSet.entity);
  const loading = useAppSelector(state => state.educationLevelCodeSet.loading);
  const updating = useAppSelector(state => state.educationLevelCodeSet.updating);
  const updateSuccess = useAppSelector(state => state.educationLevelCodeSet.updateSuccess);
  const handleClose = () => {
    props.history.push('/education-level-code-set' + props.location.search);
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
      ...educationLevelCodeSetEntity,
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
          ...educationLevelCodeSetEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="csc2022App.educationLevelCodeSet.home.createOrEditLabel" data-cy="EducationLevelCodeSetCreateUpdateHeading">
            <Translate contentKey="csc2022App.educationLevelCodeSet.home.createOrEditLabel">
              Create or edit a EducationLevelCodeSet
            </Translate>
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
                  id="education-level-code-set-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('csc2022App.educationLevelCodeSet.code')}
                id="education-level-code-set-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.educationLevelCodeSet.codeId')}
                id="education-level-code-set-codeId"
                name="codeId"
                data-cy="codeId"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.educationLevelCodeSet.labelEn')}
                id="education-level-code-set-labelEn"
                name="labelEn"
                data-cy="labelEn"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.educationLevelCodeSet.labelFi')}
                id="education-level-code-set-labelFi"
                name="labelFi"
                data-cy="labelFi"
                type="text"
              />
              <ValidatedField
                label={translate('csc2022App.educationLevelCodeSet.labelSv')}
                id="education-level-code-set-labelSv"
                name="labelSv"
                data-cy="labelSv"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/education-level-code-set" replace color="info">
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

export default EducationLevelCodeSetUpdate;
