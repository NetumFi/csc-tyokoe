import './search-material.scss';

import React, {useEffect} from 'react';
import {Link} from 'react-router-dom';
import {translate, Translate, ValidatedField, ValidatedForm} from 'react-jhipster';
import {
  Row,
  Col,
  Alert,
  Button,
  Input,
  Badge,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Container
} from 'reactstrap';

import {useAppDispatch, useAppSelector} from 'app/config/store';
import {toast} from "react-toastify";
import {handleSearch, addFilter, deleteFilter} from "app/modules/search-material/search-material.reducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


export const SearchMaterial = () => {
  const account = useAppSelector(state => state.authentication.account);
  const searchparams = useAppSelector(state => state.searchMaterial);
  const dispatch = useAppDispatch();

  const handleValidSubmit = ({searchterms, filter}) => {
    dispatch(handleSearch({searchTerms: searchterms, filters: filter}));
  };

  const handleAddFilter = (params) => {
    dispatch(addFilter(params.target.value));
  };

  const handleDeleteFilter = (filter) => {
    dispatch(deleteFilter(filter));
  }

  const rajausehdot = ["Abiturientti", "Alanvaihtaja", "Itsensä sivistäjä", "Työelämän tarpeista opiskeleva", "Yliopiston jatko-opiskelija"];

  const sort = ["Suosituin ensin"];

  return (
    <Container>
        <h2>
          <Translate contentKey="materialsearch.title.welcome" interpolate={{name: account?.firstName}}>
            Tervetuloa, {account?.firstName}.
          </Translate>
        </h2>
        <Container>
        <ValidatedForm id="materialsearch-form" onSubmit={handleValidSubmit} defaultValues={searchparams}>
          <Row>
            <Col sm="10">
              <Input
                type="text"
                name="searchterms"
                label={translate('materialsearch.form.searchterms')}
                id="searchterms"
                placeholder={translate('materialsearch.form.searchterms-placeholder')}
                data-cy="searchterms"
              />
            </Col>
            <Col xs="1">
              <Button color="primary" type="submit" data-cy="submit">
                <Translate contentKey="materialsearch.form.button">Search</Translate>
              </Button>
            </Col>
          </Row>
          <Row>
            <Col
              lg="2">
              <Input
                name="filter"
                label={translate('materialsearch.form.filter')}
                id="filter"
                placeholder={translate('materialsearch.form.filter')}
                type="select"
                data-cy="filter"
                onChange={handleAddFilter}
                defaultValue={null}>
                {
                  [<option key="firstElement"> {translate('materialsearch.form.filter')} </option>]
                    .concat(rajausehdot.filter(item => !searchparams.filters.includes(item)).map(rajaus => (
                      <option value={rajaus} key={rajaus}>
                        {rajaus}
                      </option>
                    )))
                }
              </Input>
            </Col>
            <Col sm="10" className="filterList">
              {searchparams.filters.map(filter =>
                <Badge
                  key={filter}
                  className="filterChip"
                  tabindex="0"
                  color="primary"
                  onClick={() => handleDeleteFilter(filter)}
                  pill>
                  {filter}
                  <span className="icon">
                    <FontAwesomeIcon icon="x"/>
                  </span>
                </Badge>)}
            </Col>
          </Row>
          <Row>
            <Col sm="2">
              <Input
                type="select">
                {sort.map(s => <option key={s}>{s}</option>)}
              </Input>
            </Col>
          </Row>
        </ValidatedForm>
        </Container>
    </Container>
  );
};

export default SearchMaterial;
