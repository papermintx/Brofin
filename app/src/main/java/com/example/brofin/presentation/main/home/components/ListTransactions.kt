import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.brofin.domain.models.BudgetingDiary
import com.example.brofin.utils.Expense
import com.example.brofin.utils.toFormattedDate
import com.example.brofin.utils.toIndonesianCurrency

@Composable
fun ListTransactions(budgetList: List<BudgetingDiary?>) {
    if (budgetList.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kamu belum menambahkan transaksi",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    } else {
       Card(
           modifier = Modifier
               .fillMaxWidth()
               .padding(vertical = 4.dp),
           shape = MaterialTheme.shapes.medium,
       ) {
           Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
           ) {
               Text(
                   text = "Transaksi Terakhir",
                   style = MaterialTheme.typography.bodyMedium,
                   color = MaterialTheme.colorScheme.onSecondary,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.padding(16.dp),
               )

               Button(
                   onClick = { /*TODO*/ },
                   modifier = Modifier
                       .padding(16.dp)
                       .align(Alignment.CenterVertically),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = MaterialTheme.colorScheme.primary,
                       contentColor = MaterialTheme.colorScheme.onPrimary
                   ),
                   border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
               ) {
                   Text(
                       text = "Lihat semua",
                       style = MaterialTheme.typography.bodySmall,
                       fontWeight = FontWeight.Bold,
                   )
               }


           }
           LazyColumn {
                items(budgetList.take(3)) { budgetingDiary ->
                     if (budgetingDiary != null) {
                          DiaryItem(budgetingDiary = budgetingDiary)
                          if (budgetList.indexOf(budgetingDiary) != budgetList.size - 1) {
                              HorizontalDivider(
                                  modifier = Modifier.padding(horizontal = 16.dp),
                                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                              )
                          }
                     }
                }
           }
       }
    }
}

@Composable
fun DiaryItem(budgetingDiary: BudgetingDiary) {
    val date =  budgetingDiary.date.toFormattedDate()
    fun trimToMaxWords(text: String, maxWords: Int): String {
        val words = text.split(" ")
        return if (words.size > maxWords) {
            words.take(maxWords).joinToString(" ") + "..."
        } else {
            text
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = Expense.getNameById(budgetingDiary.categoryId) ?: "Tidak diketahui",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (budgetingDiary.description != null){
                Text(
                    text = trimToMaxWords(budgetingDiary.description, 5),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        Text(
            text = if (budgetingDiary.isExpense)"-${budgetingDiary.amount.toIndonesianCurrency()}" else "+${budgetingDiary.amount.toIndonesianCurrency()}",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = if (budgetingDiary.isExpense) Color.Red.copy(alpha = 0.7f) else Color.Green.copy(alpha = 0.7f)
        )
    }


}
