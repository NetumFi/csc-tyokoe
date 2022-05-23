import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('AgeCodeSet e2e test', () => {
  const ageCodeSetPageUrl = '/age-code-set';
  const ageCodeSetPageUrlPattern = new RegExp('/age-code-set(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const ageCodeSetSample = {};

  let ageCodeSet: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/age-code-sets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/age-code-sets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/age-code-sets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (ageCodeSet) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/age-code-sets/${ageCodeSet.id}`,
      }).then(() => {
        ageCodeSet = undefined;
      });
    }
  });

  it('AgeCodeSets menu should load AgeCodeSets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('age-code-set');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AgeCodeSet').should('exist');
    cy.url().should('match', ageCodeSetPageUrlPattern);
  });

  describe('AgeCodeSet page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(ageCodeSetPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AgeCodeSet page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/age-code-set/new$'));
        cy.getEntityCreateUpdateHeading('AgeCodeSet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ageCodeSetPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/age-code-sets',
          body: ageCodeSetSample,
        }).then(({ body }) => {
          ageCodeSet = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/age-code-sets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/age-code-sets?page=0&size=20>; rel="last",<http://localhost/api/age-code-sets?page=0&size=20>; rel="first"',
              },
              body: [ageCodeSet],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(ageCodeSetPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AgeCodeSet page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('ageCodeSet');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ageCodeSetPageUrlPattern);
      });

      it('edit button click should load edit AgeCodeSet page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AgeCodeSet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ageCodeSetPageUrlPattern);
      });

      it('last delete button click should delete instance of AgeCodeSet', () => {
        cy.intercept('GET', '/api/age-code-sets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('ageCodeSet').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', ageCodeSetPageUrlPattern);

        ageCodeSet = undefined;
      });
    });
  });

  describe('new AgeCodeSet page', () => {
    beforeEach(() => {
      cy.visit(`${ageCodeSetPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AgeCodeSet');
    });

    it('should create an instance of AgeCodeSet', () => {
      cy.get(`[data-cy="code"]`).type('Cambridgeshire').should('have.value', 'Cambridgeshire');

      cy.get(`[data-cy="codeId"]`).type('alarm Libyan interactive').should('have.value', 'alarm Libyan interactive');

      cy.get(`[data-cy="labelEn"]`).type('archive').should('have.value', 'archive');

      cy.get(`[data-cy="labelFi"]`).type('Berkshire overriding Soft').should('have.value', 'Berkshire overriding Soft');

      cy.get(`[data-cy="labelSv"]`).type('application Som').should('have.value', 'application Som');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        ageCodeSet = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', ageCodeSetPageUrlPattern);
    });
  });
});
