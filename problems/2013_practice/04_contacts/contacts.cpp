
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


struct Contact
{
   char firstName[128];
   char lastName[128];
   char email[128][128];
   int  numEmails;
   char phone[128][16];
   int  numPhones;
};


Contact *contacts[128];
int     numContacts;


Contact *findContact(char *firstName, char *lastName)
{
   int i;

   for (i = 0; i < numContacts; i++)
   {
      if ((strcmp(firstName, contacts[i]->firstName) == 0) &&
          (strcmp(lastName, contacts[i]->lastName) == 0))
         return contacts[i];
   }

   return NULL;
}


void sortContacts()
{
   int     numPass;
   bool    exch;
   int     i;
   Contact *temp;

   // Sort by first name (using a simple bubble sort)
   numPass = numContacts-1;
   exch = true;
   while (exch)
   {
      exch = false;

      for (i = 0; i < numPass; i++)
      {
         if (strcmp(contacts[i]->firstName, contacts[i+1]->firstName) > 0)
         {
            temp = contacts[i];
            contacts[i] = contacts[i+1];
            contacts[i+1] = temp;
            exch = true;
         }
      }
   }

   // Then, sort by last name
   numPass = numContacts-1;
   exch = true;
   while (exch)
   {
      exch = false;

      for (i = 0; i < numPass; i++)
      {
         if (strcmp(contacts[i]->lastName, contacts[i+1]->lastName) > 0)
         {
            temp = contacts[i];
            contacts[i] = contacts[i+1];
            contacts[i+1] = temp;
            exch = true;
         }
      }
   }
}


void sortContactData(Contact *contact)
{
   int     numPass;
   bool    exch;
   int     i;
   char    temp[128];

   // Sort the e-mail addresses (another bubble sort)
   numPass = contact->numEmails-1;
   exch = true;
   while (exch)
   {
      exch = false;

      for (i = 0; i < numPass; i++)
      {
         if (strcmp(contact->email[i], contact->email[i+1]) > 0)
         {
            strcpy(temp, contact->email[i]);
            strcpy(contact->email[i], contact->email[i+1]);
            strcpy(contact->email[i+1], temp);
            exch = true;
         }
      }
   }

   // Sort the phone numbers (you get the idea)
   numPass = contact->numPhones-1;
   exch = true;
   while (exch)
   {
      exch = false;

      for (i = 0; i < numPass; i++)
      {
         if (strcmp(contact->phone[i], contact->phone[i+1]) > 0)
         {
            strcpy(temp, contact->phone[i]);
            strcpy(contact->phone[i], contact->phone[i+1]);
            strcpy(contact->phone[i+1], temp);
            exch = true;
         }
      }
   }
}


int main(void)
{
   FILE    *fp;
   char    line[256];
   char    *nl;
   char    *token;
   int     numEntries;
   char    firstName[128];
   char    lastName[128];
   char    contactName[128];
   Contact *contact;
   int     i, j;
   int     listNum;

   // Open the input file
   fp = fopen("contacts.in", "r");

   // Read the number of entries in the contact list
   fgets(line, sizeof(line), fp);
   numEntries = atoi(line);

   // Process the contact list
   listNum = 1;
   while (numEntries != 0)
   {
      // Flush the contact list
      memset(contacts, 0, sizeof(contacts));
      numContacts = 0;

      // Read the contacts
      for (i = 0; i < numEntries; i++)
      {
         // Get the contact entry
         fgets(line, sizeof(line), fp);

         // Get the contact's name
         token = strtok(line, " \n");
         strcpy(firstName, token);
         token = strtok(NULL, " \n");
         strcpy(lastName, token);

         // Find the contact in the list
         contact = findContact(firstName, lastName);
         if (contact == NULL)
         {
            // Create a new contact and add it to the list
            contact = new Contact;
            memset(contact, 0, sizeof(Contact));

            // Set the name
            strcpy(contact->firstName, firstName);
            strcpy(contact->lastName, lastName);
            contacts[numContacts] = contact;
            numContacts++;
         }

         // Get the contact data
         token = strtok(NULL, " \n");

         // Classify the data as phone or email address
         if (strchr(token, '@') == NULL)
         {
            // Format the phone number properly and store it in the contact's
            // list
            sprintf(contact->phone[contact->numPhones],
                    "(%c%c%c)%c%c%c-%c%c%c%c",
                    token[0], token[1], token[2],
                    token[3], token[4], token[5],
                    token[6], token[7], token[8], token[9]);
            contact->numPhones++;         
         }
         else
         {
            // Add the e-mail to the list
            strcpy(contact->email[contact->numEmails], token);
            contact->numEmails++;
         }
      }

      // Sort the contacts
      sortContacts();
      
      printf("Contact list #%d:\n", listNum);

      // Output the contacts
      for (i = 0; i < numContacts; i++)
      {
         // Sort the contact's data
         sortContactData(contacts[i]);

         printf("%s %s\n", contacts[i]->firstName, contacts[i]->lastName);
         printf("Phone:\n");
         for (j = 0; j < contacts[i]->numPhones; j++)
            printf("%s\n", contacts[i]->phone[j]);

         printf("E-Mail:\n");
         for (j = 0; j < contacts[i]->numEmails; j++)
            printf("%s\n", contacts[i]->email[j]);

         printf("###\n");
      }

      // Print the blank line
      printf("\n");

      // Flush the contact list
      for (i = 0; i < numContacts; i++)
         delete contacts[i];

      // Read the number of entries in the next contact list
      fgets(line, sizeof(line), fp);
      numEntries = atoi(line);
      
      listNum++;
   }

   fclose(fp);
}
