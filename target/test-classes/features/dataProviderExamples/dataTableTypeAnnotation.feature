Feature: data provider demo

  @DataProv
  Scenario: DataTable to List of java objects
    Given the following books
      | title                  | author              | yearOfPublishing |
      | To kill a mockingbird  | Harper Lee          | 1960             |
      | The catcher in the rye | J.D. Salinger       | 1951             |
      | The great Gatsby       | F. Scott Fitzgerald | 1925             |

  @DataProv
  Scenario: DataTable to Map<String, Object>
    Given the following data
      | title                  | yearOfPublishing |
      | To kill a mockingbird  | 1960             |
      | The catcher in the rye | 1951             |
      | The great Gatsby       | 1925             |