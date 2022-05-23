import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Age e2e test', () => {
  const agePageUrl = '/age';
  const agePageUrlPattern = new RegExp('/age(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const ageSample = {};

  let age: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ages+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ages').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ages/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (age) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ages/${age.id}`,
      }).then(() => {
        age = undefined;
      });
    }
  });

  it('Ages menu should load Ages page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('age');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Age').should('exist');
    cy.url().should('match', agePageUrlPattern);
  });

  describe('Age page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(agePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Age page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/age/new$'));
        cy.getEntityCreateUpdateHeading('Age');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', agePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ages',
          body: ageSample,
        }).then(({ body }) => {
          age = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ages+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/ages?page=0&size=20>; rel="last",<http://localhost/api/ages?page=0&size=20>; rel="first"',
              },
              body: [age],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(agePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Age page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('age');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', agePageUrlPattern);
      });

      it('edit button click should load edit Age page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Age');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', agePageUrlPattern);
      });

      it('last delete button click should delete instance of Age', () => {
        cy.intercept('GET', '/api/ages/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('age').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', agePageUrlPattern);

        age = undefined;
      });
    });
  });

  describe('new Age page', () => {
    beforeEach(() => {
      cy.visit(`${agePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Age');
    });

    it('should create an instance of Age', () => {
      cy.get(`[data-cy="code"]`).type('Rupee quantifying communities').should('have.value', 'Rupee quantifying communities');

      cy.get(`[data-cy="codeId"]`).type('front-end Dynamic solution-oriented').should('have.value', 'front-end Dynamic solution-oriented');

      cy.get(`[data-cy="labelEn"]`).type('heuristic').should('have.value', 'heuristic');

      cy.get(`[data-cy="labelFi"]`).type('communities Shirt').should('have.value', 'communities Shirt');

      cy.get(`[data-cy="labelSv"]`).type('throughput Buckinghamshire').should('have.value', 'throughput Buckinghamshire');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        age = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', agePageUrlPattern);
    });
  });
});
