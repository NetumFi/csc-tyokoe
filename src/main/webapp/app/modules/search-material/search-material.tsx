import './search-material.scss';

import React, {useEffect} from 'react';
import { Link } from 'react-router-dom';
import {translate, Translate, ValidatedField, ValidatedForm} from 'react-jhipster';
import {Row, Col, Alert, Button} from 'reactstrap';

import {useAppDispatch, useAppSelector} from 'app/config/store';
import {toast} from "react-toastify";
import {handleSearch, addFilter, deleteFilter} from "app/modules/search-material/search-material.reducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export const SearchMaterial = () => {
  const account = useAppSelector(state => state.authentication.account);
  const searchparams = useAppSelector(state => state.searchMaterial);
  const dispatch = useAppDispatch();

  const handleValidSubmit = ({ searchterms, filter }) => {
    dispatch(handleSearch({ searchTerms: searchterms, filters: filter }));
  };

  const handleAddFilter = (params) => {
    dispatch(addFilter(params.target.value));
  };

  const handleDeleteFilter = (filter) => {
    dispatch(deleteFilter(filter));
  }

  const rajausehdot = ["Abiturientti", "Alanvaihtaja", "Itsensä sivistäjä", "Työelämän tarpeista opiskeleva", "Yliopiston jatko-opiskelija"];


  return (
    <Row>
      <Col md="9">
        <h2>
          <Translate contentKey="materialsearch.title.welcome" interpolate={{ name: account.username }}>
            Tervetuloa, {account.username}.
          </Translate>
        </h2>
        <ValidatedForm id="materialsearch-form" onSubmit={handleValidSubmit} defaultValues={searchparams}>
          <ValidatedField
            name="searchterms"
            label={translate('materialsearch.form.searchterms')}
            id="searchterms"
            placeholder={translate('materialsearch.form.searchterms-placeholder')}
            data-cy="searchterms"
          />
          <ValidatedField
            name="filter"
            label={translate('materialsearch.form.filter')}
            id="filter"
            placeholder={translate('materialsearch.form.filter')}
            type="select"
            data-cy="filter"
            onChange={handleAddFilter}>
            {
              [<option selected key="firstElement"> {translate('materialsearch.form.filter')} </option>]
                .concat(rajausehdot.filter(item => !searchparams.filters.includes(item)).map(rajaus => (
                  <option value={rajaus} key={rajaus}>
                    {rajaus}
                  </option>
                )))
            }
          </ValidatedField>
          <div className="filterList">
          {searchparams.filters.map(filter =>
            <span
              key={filter}
              className="badge bg-primary filterChip"
              onClick={() => handleDeleteFilter(filter)}>
              {filter}
              &nbsp;
              <FontAwesomeIcon icon="trash" />
            </span>)}
          </div>
          <Button color="primary" type="submit" data-cy="submit">
            <Translate contentKey="materialsearch.form.button">Search</Translate>
          </Button>
        </ValidatedForm>
      </Col>
    </Row>
  );
};

export default SearchMaterial;
