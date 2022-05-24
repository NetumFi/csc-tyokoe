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

describe('EducationLevelCodeSet e2e test', () => {
  const educationLevelCodeSetPageUrl = '/education-level-code-set';
  const educationLevelCodeSetPageUrlPattern = new RegExp('/education-level-code-set(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'admin';
  const password = Cypress.env('E2E_PASSWORD') ?? 'admin';
  const educationLevelCodeSetSample = {};

  let educationLevelCodeSet: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/education-level-code-sets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/education-level-code-sets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/education-level-code-sets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (educationLevelCodeSet) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/education-level-code-sets/${educationLevelCodeSet.id}`,
      }).then(() => {
        educationLevelCodeSet = undefined;
      });
    }
  });

  it('EducationLevelCodeSets menu should load EducationLevelCodeSets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('education-level-code-set');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EducationLevelCodeSet').should('exist');
    cy.url().should('match', educationLevelCodeSetPageUrlPattern);
  });

  describe('EducationLevelCodeSet page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(educationLevelCodeSetPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EducationLevelCodeSet page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/education-level-code-set/new$'));
        cy.getEntityCreateUpdateHeading('EducationLevelCodeSet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', educationLevelCodeSetPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/education-level-code-sets',
          body: educationLevelCodeSetSample,
        }).then(({ body }) => {
          educationLevelCodeSet = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/education-level-code-sets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/education-level-code-sets?page=0&size=20>; rel="last",<http://localhost/api/education-level-code-sets?page=0&size=20>; rel="first"',
              },
              body: [educationLevelCodeSet],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(educationLevelCodeSetPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details EducationLevelCodeSet page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('educationLevelCodeSet');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', educationLevelCodeSetPageUrlPattern);
      });

      it('edit button click should load edit EducationLevelCodeSet page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EducationLevelCodeSet');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', educationLevelCodeSetPageUrlPattern);
      });

      it('last delete button click should delete instance of EducationLevelCodeSet', () => {
        cy.intercept('GET', '/api/education-level-code-sets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('educationLevelCodeSet').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', educationLevelCodeSetPageUrlPattern);

        educationLevelCodeSet = undefined;
      });
    });
  });

  describe('new EducationLevelCodeSet page', () => {
    beforeEach(() => {
      cy.visit(`${educationLevelCodeSetPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EducationLevelCodeSet');
    });

    it('should create an instance of EducationLevelCodeSet', () => {
      cy.get(`[data-cy="code"]`).type('Dynamic Card').should('have.value', 'Dynamic Card');

      cy.get(`[data-cy="codeId"]`).type('mission-critical Gloves').should('have.value', 'mission-critical Gloves');

      cy.get(`[data-cy="labelEn"]`).type('Mauritius').should('have.value', 'Mauritius');

      cy.get(`[data-cy="labelFi"]`).type('encoding Future Frozen').should('have.value', 'encoding Future Frozen');

      cy.get(`[data-cy="labelSv"]`).type('revolutionize').should('have.value', 'revolutionize');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        educationLevelCodeSet = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', educationLevelCodeSetPageUrlPattern);
    });
  });
});
